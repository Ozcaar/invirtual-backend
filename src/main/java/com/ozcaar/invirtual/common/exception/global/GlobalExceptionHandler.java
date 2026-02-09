package com.ozcaar.invirtual.common.exception.global;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // ---------- Security ----------
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
        return build(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Credenciales inválidas");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
        return build(HttpStatus.FORBIDDEN, "FORBIDDEN",
                defaultIfBlank(ex.getMessage(), "No tienes permiso para realizar esta acción"));
    }

    // ---------- Custom domain exceptions ----------
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "NOT_FOUND", defaultIfBlank(ex.getMessage(), "No encontrado."));
    }

    @ExceptionHandler({ ConflictException.class, AlreadyExistsException.class })
    public ResponseEntity<ApiError> handleConflict(RuntimeException ex) {
        return build(HttpStatus.CONFLICT, "CONFLICT", defaultIfBlank(ex.getMessage(), "Conflicto."));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbidden(ForbiddenException ex) {
        return build(HttpStatus.FORBIDDEN, "FORBIDDEN", defaultIfBlank(ex.getMessage(), "Prohibido."));
    }

    @ExceptionHandler(GlobalBadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(GlobalBadRequestException ex) {
        return build(HttpStatus.BAD_REQUEST, "BAD_REQUEST", defaultIfBlank(ex.getMessage(), "Solicitud inválida."));
    }

    // ---------- Validation ----------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiFieldsError> handleManve(MethodArgumentNotValidException ex) {
        var details = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldDetail)
                .toList();

        return buildFields(HttpStatus.BAD_REQUEST, "BAD_REQUEST", details);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiFieldsError> handleHmve(HandlerMethodValidationException ex) {
        var details = ex.getAllErrors().stream()
                .map(this::toFieldDetail)
                .toList();

        return buildFields(HttpStatus.BAD_REQUEST, "BAD_REQUEST", details);
    }

    private ApiFieldsError.FieldErrorDetail toFieldDetail(MessageSourceResolvable err) {
        if (err instanceof FieldError fe) {
            return new ApiFieldsError.FieldErrorDetail(
                    fe.getField(),
                    fe.getDefaultMessage(),
                    fe.getRejectedValue() == null ? null : String.valueOf(fe.getRejectedValue()),
                    "Error de validación en los datos enviados.");
        }

        if (err instanceof ObjectError oe) {
            return new ApiFieldsError.FieldErrorDetail(
                    oe.getObjectName(),
                    oe.getDefaultMessage(),
                    null,
                    "Error de validación en los datos enviados.");
        }

        // fallback por si llega otro tipo
        return new ApiFieldsError.FieldErrorDetail(
                "request",
                "Error de validación en los datos enviados.",
                null,
                "Error de validación en los datos enviados.");
    }

    // ---------- Bad JSON / request ----------
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleJsonParseError(HttpMessageNotReadableException ex) {
        log.warn("Malformed JSON request", ex);
        return build(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "JSON inválido o mal formado.");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ApiError> handleInvalidDataAccess(InvalidDataAccessApiUsageException ex) {
        log.warn("Invalid data access usage", ex);
        return build(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Solicitud inválida.");
    }

    // ---------- Fallback ----------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "Error interno.");
    }

    // ---------- Helpers ----------
    private ResponseEntity<ApiError> build(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), code, message));
    }

    private ResponseEntity<ApiFieldsError> buildFields(HttpStatus status, String code,
            List<ApiFieldsError.FieldErrorDetail> details) {
        return ResponseEntity.status(status)
                .body(new ApiFieldsError(status.value(), code, details));
    }

    private String defaultIfBlank(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value.trim();
    }
}
