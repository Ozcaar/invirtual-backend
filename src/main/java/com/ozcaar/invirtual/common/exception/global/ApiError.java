package com.ozcaar.invirtual.common.exception.global;

import java.time.LocalDateTime;

public class ApiError {

    private ErrorDetailv2 error;

    public ApiError(Integer code, String errortype, String message) {
        this.error = new ErrorDetailv2(code, errortype, message);
    }

    public ErrorDetailv2 getError() {
        return error;
    }

    public static class ErrorDetailv2 {
        private Integer code;
        private String errortype;

        private String message;
        private LocalDateTime timestamp;

        public ErrorDetailv2(Integer code, String errortype , String message) {
            this.code = code;
            this.errortype = errortype;
            this.message = message;
            this.timestamp = LocalDateTime.now();
        }

        // Getters & setters
        
        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getErrortype() {
            return errortype;
        }

        public void setErrortype(String errortype) {
            this.errortype = errortype;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

    }
}
