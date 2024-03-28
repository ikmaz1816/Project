package com.project.employeeleave.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Employee(Long id, String firstname, String lastname, String email) {
}
