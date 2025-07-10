package com.example.exception;

/**
 * This exception is to be used when a user attempts to register an account with an already existing username
 */
public class DuplicateUsernameException extends Exception{
    public DuplicateUsernameException(String message){
        super(message);
    }
}
