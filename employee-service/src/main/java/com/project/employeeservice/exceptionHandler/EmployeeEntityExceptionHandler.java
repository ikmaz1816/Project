package com.project.employeeservice.exceptionHandler;

import com.project.employeeservice.entity.EmployeeError;
import com.project.employeeservice.exception.EmployeeNotFoundException;
import com.project.employeeservice.exception.TokenNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class EmployeeEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<EmployeeError> getResponse(EmployeeNotFoundException exception)
    {
        EmployeeError employeeError=new EmployeeError(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(employeeError);
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<EmployeeError> getResponse(TokenNotValidException exception)
    {
        EmployeeError employeeError=new EmployeeError(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(employeeError);
    }

}
