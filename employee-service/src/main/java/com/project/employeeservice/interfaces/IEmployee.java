package com.project.employeeservice.interfaces;

import com.project.employeeservice.dto.EmployeeDTO;
import org.springframework.stereotype.Service;

@Service
public interface IEmployee {
    EmployeeDTO getEmployeeInfo(String email);
}
