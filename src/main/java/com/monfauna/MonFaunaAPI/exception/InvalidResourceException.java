package com.monfauna.MonFaunaAPI.exception;

public class InvalidResourceException extends RuntimeException{
    public InvalidResourceException(String message) {
        super(message);
    }
}
