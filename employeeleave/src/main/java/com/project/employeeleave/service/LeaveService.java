package com.project.employeeleave.service;

import com.netflix.discovery.EurekaClient;
import com.project.employeeleave.dto.LeaveDTO;
import com.project.employeeleave.entity.Employee;
import com.project.employeeleave.entity.Leave;
import com.project.employeeleave.exception.EmployeeNotFoundException;
import com.project.employeeleave.interfaces.ILeave;
import com.project.employeeleave.repository.LeaveRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class LeaveService implements ILeave {

    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EurekaClient discoveryClient;
    @Override
    public LeaveDTO getLeave(String email) throws EmployeeNotFoundException{
        Leave details = leaveRepository.findByEmpEmail(email).orElseThrow(
                () -> new EmployeeNotFoundException("Employee with email "+ email +" not found")
        );
        return new LeaveDTO(details.getLeaveId(), details.getType(), details.getAvailable(),
                details.getBooked(), details.getEmployee());
    }

    @Override
    public boolean getEmployeeDetails(String email, HttpServletRequest request, HttpServletResponse response) throws EmployeeNotFoundException {
        String token = "";
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("accessToken")){
                    token = cookie.getValue();
                }
            }
        }
        final String finalToken = token;
        String url = discoveryClient.getNextServerFromEureka("EMPLOYEE-SERVICE",false).getHomePageUrl();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("email", email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestParams, headers);

        restTemplate.getInterceptors().add((req, body, execution) -> {
            req.getHeaders().add(HttpHeaders.COOKIE, "accessToken="+finalToken);
            return execution.execute(req, body);
        });

        try{
            ResponseEntity<Employee> entity = restTemplate.exchange(
                    url+"/employee/getEmployee",
                    HttpMethod.POST,
                    requestEntity,
                    Employee.class
            );
        }catch (HttpClientErrorException e){
            throw new EmployeeNotFoundException("Employee not found !!");
        }
        return true;
    }

}
