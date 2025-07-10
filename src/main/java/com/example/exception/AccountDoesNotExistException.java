package com.example.exception;

/**
 * This exception is to be used if a login fails as a result of an account not existing.
 */
public class AccountDoesNotExistException extends Exception{
    public AccountDoesNotExistException(String message){
        super(message);
    }
}
