package com.project.employeeleave.exceptionHandler;

import com.project.employeeleave.entity.EmployeeError;
import com.project.employeeleave.exception.EmployeeNotFoundException;
import com.project.employeeleave.exception.TokenNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class DataNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<EmployeeError> employeeNotFoundException(EmployeeNotFoundException exception, WebRequest request){
        EmployeeError error = new EmployeeError(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<EmployeeError> tokenNotValidException(TokenNotValidException tokenNotValidException, WebRequest request){
        EmployeeError token = new EmployeeError(HttpStatus.FORBIDDEN, tokenNotValidException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(token);
    }
}
