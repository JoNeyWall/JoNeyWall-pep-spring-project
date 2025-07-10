package com.example.exception;

/**
 * This exception is to be used as a general response to an unsuccessful account registration attempt.
 */
public class RegistrationException extends Exception{
    public RegistrationException(String message){
        super(message);
    }
}
