package com.example.todo_list_clean_arch.infra.handler;

import java.util.List;

public class ErrorMessage {

    private Integer status;
    private String timestamp;
    private String code;
    private String message;
    private List<ErrorMessage> details;

    private ErrorMessage() {}

    public Integer getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorMessage> getDetails() {
        return details;
    }

    public static class ErrorMessageBuilder {
        private final ErrorMessage instance;

        public ErrorMessageBuilder() {
            this.instance = new ErrorMessage();
        }

        public ErrorMessageBuilder status(Integer status) {
            instance.status = status;
            return this;
        }

        public ErrorMessageBuilder timestamp(String timestamp) {
            instance.timestamp = timestamp;
            return this;
        }

        public ErrorMessageBuilder code(String code) {
            instance.code = code;
            return this;
        }

        public ErrorMessageBuilder message(String message) {
            instance.message = message;
            return this;
        }

        public ErrorMessageBuilder details(List<ErrorMessage> details) {
            instance.details = details;
            return this;
        }

        public ErrorMessage build() {
            return instance;
        }
    }

    public static ErrorMessageBuilder builder() {
        return new ErrorMessageBuilder();
    }
}
