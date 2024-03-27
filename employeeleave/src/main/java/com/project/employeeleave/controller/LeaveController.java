package com.project.employeeleave.controller;

import com.project.employeeleave.exception.EmployeeNotFoundException;
import com.project.employeeleave.service.LeaveService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    @GetMapping("")
    public String hello(){
        return "hello";
    }

    @PostMapping("/getLeaveDetails")
    public ResponseEntity<Object> getLeaveDetails(@RequestParam String email, HttpServletRequest request, HttpServletResponse response) throws EmployeeNotFoundException {
        if(leaveService.getEmployeeDetails(email, request, response)){
            var data = leaveService.getLeave(email);
            return ResponseEntity.status(HttpStatus.OK).body(data);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee with the email "+ email + " found");
    }
}
