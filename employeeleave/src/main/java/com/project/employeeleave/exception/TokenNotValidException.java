package com.project.employeeleave.exception;

public class TokenNotValidException extends RuntimeException{
    public TokenNotValidException(String message){
        super(message);
    }
}
