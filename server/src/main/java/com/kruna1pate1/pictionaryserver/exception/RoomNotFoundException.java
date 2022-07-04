package com.kruna1pate1.pictionaryserver.exception;

/**
 * Created by KRUNAL on 13-05-2022
 */
public class RoomNotFoundException extends NotFoundException {

    public RoomNotFoundException() {
        super();
    }

    public RoomNotFoundException(String msg) {
        super(msg);
    }

    public RoomNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
