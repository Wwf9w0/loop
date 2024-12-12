package com.loop.service.advice;

import lombok.Getter;

@Getter
public class StorageException extends RuntimeException{

    private final int code;

    public StorageException(String message,int errorCode) {
        super(message);
        this.code = errorCode;
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
        this.code = 20002;
    }
}
