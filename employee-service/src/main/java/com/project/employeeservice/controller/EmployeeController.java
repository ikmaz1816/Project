package com.project.employeeservice.controller;

import com.project.employeeservice.dto.EmployeeDTO;
import com.project.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/getEmployee")
    public ResponseEntity<EmployeeDTO> getEmployee(@RequestParam String email)
    {
        EmployeeDTO employeeDTO=employeeService.getEmployeeInfo(email);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @GetMapping("/")
    public String hello()
    {
        return "Hello";
    }
}
