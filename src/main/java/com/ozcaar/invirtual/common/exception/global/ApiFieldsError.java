package com.ozcaar.invirtual.common.exception.global;

import java.time.LocalDateTime;
import java.util.List;

public class ApiFieldsError {

    // // Version 1

    // private int status;
    // private String message;
    // private LocalDateTime timestamp;

    // public ApiErrorV1(int status, String message) {
    //     this.status = status;
    //     this.message = message + ".";
    //     this.timestamp = LocalDateTime.now();
    // }

    // // Getters & setters
    

    // Version 2

    private ErrorDetail error;
    
    // public ApiError(String code, String message, List<FieldErrorDetail> details, String documentationUrl) {
    //     this.error = new ErrorDetail(code, message, details, documentationUrl);
    // }

    public ApiFieldsError(String code, List<FieldErrorDetail> details) {
        this.error = new ErrorDetail(code, details);
    }

    public ErrorDetail getError() {
        return error;
    }

    public static class ErrorDetail {
        private String code;
        // private String message;
        private List<FieldErrorDetail> details;
        // private String documentation;
        private LocalDateTime timestamp;

        // public void setDocumentation(String documentation) {
        //     this.documentation = documentation;
        // }

        // public ErrorDetail(String code, String message, List<FieldErrorDetail> details, String documentation) {
        public ErrorDetail(String code, List<FieldErrorDetail> details) {
            this.code = code;
            // this.message = message;
            this.details = details;
            // this.documentation = documentation;
            this.timestamp = LocalDateTime.now();

        }

        // Getters & setters
        
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        // public String getMessage() {
        //     return message;
        // }

        // public void setMessage(String message) {
        //     this.message = message;
        // }

        public List<FieldErrorDetail> getDetails() {
            return details;
        }

        public void setDetails(List<FieldErrorDetail> details) {
            this.details = details;
        }

        // public String getDocumentation() {
        //     return documentation;
        // }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

    }

    public static class FieldErrorDetail {
        private String field;
        private String issue;
        private String provided;
        private String expected;

        public FieldErrorDetail(String field, String issue, String provided, String expected) {
            this.field = field;
            this.issue = issue;
            this.provided = provided;
            this.expected = expected;
        }

        // Getters & setters

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getProvided() {
            return provided;
        }

        public void setProvided(String provided) {
            this.provided = provided;
        }

        public String getExpected() {
            return expected;
        }

        public void setExpected(String expected) {
            this.expected = expected;
        }
    }
}

