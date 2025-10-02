package com.ozcaar.invirtual.common.exception.global;

import java.util.List;

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

@ControllerAdvice 
public class GlobalExceptionHandler {

    // Authorization exceptions

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
        ApiError error = new ApiError(
            HttpStatus.UNAUTHORIZED.value(),
            "UNAUTHORIZED", 
            "Credenciales inválidas"
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
        ApiError error = new ApiError(
            HttpStatus.FORBIDDEN.value(),
            "FORBIDDEN",
            ex.getMessage().isBlank() ? "No tienes permiso para realizar esta acción" : ex.getMessage().trim()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // Global exceptions

    // Custom class
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        ApiError error = new ApiError(
            HttpStatus.NOT_FOUND.value(), 
            "NOT_FOUND",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Custom class
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiError> handleExists(AlreadyExistsException ex) {
        ApiError error = new ApiError(
            HttpStatus.CONFLICT.value(),
            "CONFLICT",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler({ MethodArgumentNotValidException.class, HandlerMethodValidationException.class })
    public ResponseEntity<ApiFieldsError> handleValidationExceptions(Exception ex) {
        List<ApiFieldsError.FieldErrorDetail> fieldErrorDetail;

        if (ex instanceof MethodArgumentNotValidException manve) {
            fieldErrorDetail = manve.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiFieldsError.FieldErrorDetail(
                    error.getField(),
                    error.getDefaultMessage(),
                    String.valueOf(error.getRejectedValue()),
                    "Error de validación en los datos enviados."))
                .toList();

        } else if (ex instanceof HandlerMethodValidationException hmve) {
            fieldErrorDetail = hmve.getAllErrors()
                .stream()
                .map(err -> {
                    ObjectError error = (ObjectError) err;
                    if (error instanceof FieldError fe) {
                        return new ApiFieldsError.FieldErrorDetail(
                            fe.getField(),
                            fe.getDefaultMessage(),
                            String.valueOf(fe.getRejectedValue()),
                            "Error de validación en los datos enviados.");
                    } else {
                        return new ApiFieldsError.FieldErrorDetail(
                            error.getObjectName(),
                            error.getDefaultMessage(),
                            null,
                            "Error de validación en los datos enviados.");
                    }
                })
                .toList();
        } else {
            fieldErrorDetail = List.of();
        }

        ApiFieldsError error = new ApiFieldsError(
            HttpStatus.BAD_REQUEST.value(),
            "BAD_REQUEST",
            fieldErrorDetail
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleJsonParseError(HttpMessageNotReadableException ex) {
        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            "BAD_REQUEST",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ApiError> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            "BAD_REQUEST",
            "El ID proporcionado no puede ser nulo."
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        ApiError error = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_SERVER_ERROR",
            ex.getMessage().isBlank() ? "Error interno." : ex.getMessage().trim()
        );
        ex.printStackTrace();

        System.err.println(ex);
        System.err.println(error);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
