package com.kruna1pate1.pictionaryserver.exception;

/**
 * Created by KRUNAL on 13-05-2022
 */
public class NotFoundException extends Exception {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
