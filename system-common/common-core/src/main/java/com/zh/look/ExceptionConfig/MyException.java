package com.zh.look.ExceptionConfig;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException{
    private Integer errorCode;

    public MyException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
