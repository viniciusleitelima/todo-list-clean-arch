package com.example.todo_list_clean_arch.domain.exception;

public class BusinessException extends RuntimeException{
    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    private final String code;
    private final transient Object[] args;

    public BusinessException(String codeMessage, Object... args) {
        super(codeMessage);
        this.code = codeMessage;
        this.args = args;
    }
}
