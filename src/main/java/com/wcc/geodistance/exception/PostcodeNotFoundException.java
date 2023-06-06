package com.wcc.geodistance.exception;

public class PostcodeNotFoundException extends Exception {

    public PostcodeNotFoundException(String message) {
        super(message);
    }

    public PostcodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
