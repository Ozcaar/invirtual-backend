package com.ozcaar.invirtual.common.exception.global;

import java.time.LocalDateTime;

public class ApiError {

    private ErrorDetailv2 error;

    public ApiError(String code, String message) {
        this.error = new ErrorDetailv2(code, message);
    }

    public ErrorDetailv2 getError() {
        return error;
    }

    public static class ErrorDetailv2 {
        private String code;
        private String message;
        private LocalDateTime timestamp;

        public ErrorDetailv2(String code, String message) {
            this.code = code;
            this.message = message;
            this.timestamp = LocalDateTime.now();
        }

        // Getters & setters
        
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
