package com.project.idp.exceptionHandler;

import com.project.idp.entity.IdentityErrorResponse;
import com.project.idp.exception.CredentialsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class IdentityProviderResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CredentialsNotFoundException.class)
    public ResponseEntity<IdentityErrorResponse> getResponse(CredentialsNotFoundException credentialsNotFoundException,
                                                             WebRequest request)
    {
        IdentityErrorResponse identityErrorResponse=new IdentityErrorResponse(HttpStatus.NOT_FOUND,
                credentialsNotFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(identityErrorResponse);
    }
}
