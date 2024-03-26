package com.project.employeeservice.service;

import com.project.employeeservice.dto.EmployeeDTO;
import com.project.employeeservice.entity.Employee;
import com.project.employeeservice.exception.EmployeeNotFoundException;
import com.project.employeeservice.interfaces.IEmployee;
import com.project.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployee {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDTO getEmployeeInfo(String email) {
        Employee employee=this.employeeRepository.findByEmail(email)
                .orElseThrow(()->new EmployeeNotFoundException("Employee Not Found"));
        return new EmployeeDTO(employee.getId(), employee.getFirstname(),
                employee.getLastname(), employee.getHobbies(), employee.getEmail());
    }
}
