package com.project.employeeservice.entity;

import org.springframework.http.HttpStatus;

public record EmployeeError(HttpStatus status,String message) {
}
