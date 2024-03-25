package com.project.idp.entity;

import org.springframework.http.HttpStatus;

public record IdentityErrorResponse(HttpStatus status,String message) {
}
