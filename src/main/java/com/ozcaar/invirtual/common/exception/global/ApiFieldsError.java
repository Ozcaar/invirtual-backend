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

    public ApiFieldsError(Integer code, String errortype, List<FieldErrorDetail> details) {
        this.error = new ErrorDetail(code, errortype, details);
    }

    public ErrorDetail getError() {
        return error;
    }

    public static class ErrorDetail {
        private Integer code;
        private String errortype;
        // private String message;
        private List<FieldErrorDetail> details;
        // private String documentation;
        private LocalDateTime timestamp;

        // public void setDocumentation(String documentation) {
        //     this.documentation = documentation;
        // }

        // public ErrorDetail(String code, String message, List<FieldErrorDetail> details, String documentation) {
        public ErrorDetail(Integer code, String errortype, List<FieldErrorDetail> details) {
            this.code = code;
            this.errortype = errortype;
            // this.message = message;
            this.details = details;
            // this.documentation = documentation;
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
        private String detail;

        public FieldErrorDetail(String field, String issue, String provided, String detail) {
            this.field = field;
            this.issue = issue;
            this.provided = provided;
            this.detail = detail;
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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}

