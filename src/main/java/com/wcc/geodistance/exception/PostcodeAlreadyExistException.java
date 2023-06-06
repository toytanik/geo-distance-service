package com.wcc.geodistance.exception;

public class PostcodeAlreadyExistException extends Exception {

    public PostcodeAlreadyExistException(String message) {
        super(message);
    }

    public PostcodeAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
