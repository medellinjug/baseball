package org.medellinjug.baseball.strategy.model.exception;

public class PlayerNoFoundException extends RuntimeException{

    public PlayerNoFoundException() {
    }

    public PlayerNoFoundException(String message) {
        super(message);
    }

    public PlayerNoFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerNoFoundException(Throwable cause) {
        super(cause);
    }

    public PlayerNoFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
