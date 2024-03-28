package com.project.employeeleave.entity;

import org.springframework.http.HttpStatus;

public record EmployeeError(HttpStatus httpStatus, String message) {
}
