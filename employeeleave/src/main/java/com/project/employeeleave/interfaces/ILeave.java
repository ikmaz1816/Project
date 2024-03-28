package com.project.employeeleave.interfaces;

import com.project.employeeleave.dto.LeaveDTO;
import com.project.employeeleave.exception.EmployeeNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface ILeave {
    LeaveDTO getLeave(String email) throws EmployeeNotFoundException;

    boolean getEmployeeDetails(String email, HttpServletRequest request, HttpServletResponse response) throws EmployeeNotFoundException;

}
