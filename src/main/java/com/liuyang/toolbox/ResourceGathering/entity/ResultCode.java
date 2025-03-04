package com.liuyang.toolbox.ResourceGathering.entity;


public enum ResultCode {

    // General Success
    SUCCESS(0,"Success"),

    // General Faild
    FAILD(1,"Faild");

    private final Integer code;
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}