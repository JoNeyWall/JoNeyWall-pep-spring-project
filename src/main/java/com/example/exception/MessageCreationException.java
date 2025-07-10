package com.example.exception;

/**
 * This exception is to be used when a message is not created for any reason.
 */
public class MessageCreationException extends Exception{
    public MessageCreationException(String message){
        super(message);
    }
}
