package com.kruna1pate1.pictionaryserver.exception;

/**
 * Created by KRUNAL on 13-05-2022
 */
public class RoomOutOfCapacityException extends Exception {

    public RoomOutOfCapacityException() {
        super();
    }

    public RoomOutOfCapacityException(String msg) {
        super(msg);
    }

    public RoomOutOfCapacityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
