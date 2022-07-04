package com.kruna1pate1.pictionaryserver.exception;

/**
 * Created by KRUNAL on 13-05-2022
 */
public class PlayerNotFoundException extends NotFoundException {

    public PlayerNotFoundException() {
        super();
    }

    public PlayerNotFoundException(String msg) {
        super(msg);
    }

    public PlayerNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
