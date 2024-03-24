package com.project.employeeservice.dto;

import com.project.employeeservice.enums.Roles;

public record EmployeeDTO(long id, String firstname, String lastname, String hobbies,String email,Roles roles)
{

}
