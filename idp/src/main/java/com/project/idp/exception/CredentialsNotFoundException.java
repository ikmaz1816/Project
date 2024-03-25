package com.project.idp.exception;

public class CredentialsNotFoundException extends RuntimeException{
    public CredentialsNotFoundException(String message)
    {
        super(message);
    }
}
