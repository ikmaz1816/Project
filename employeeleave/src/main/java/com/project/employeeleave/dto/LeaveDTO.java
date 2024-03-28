package com.project.employeeleave.dto;

import com.project.employeeleave.entity.Employee;
import com.project.employeeleave.enums.LeaveType;

public record LeaveDTO(long LeaveId, LeaveType type, int available, int booked, Employee employee) {
}
