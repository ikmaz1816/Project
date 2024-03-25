package com.project.idp.controller;

import com.project.idp.dto.IdentityResponse;
import com.project.idp.dto.TokenResponse;
import com.project.idp.entity.Identity;
import com.project.idp.entity.IdentityLogin;
import com.project.idp.interfaces.IdentityInterface;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("identity")
public class IdentityController
{
    @Value("${jwt.expiration}")
    private long expiration;
    @Autowired
    private IdentityInterface identityInterface;

    @PostMapping("/register")
    public ResponseEntity<IdentityResponse> register(@RequestBody Identity identity)
    {
        IdentityResponse identityResponse = identityInterface.register(identity);
        return ResponseEntity.status(HttpStatus.OK).body(identityResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody IdentityLogin identityLogin, HttpServletResponse response)
    {
        TokenResponse tokenResponse = identityInterface.login(identityLogin);
        ResponseCookie responseCookie = ResponseCookie.from("accessToken", tokenResponse.token())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(expiration)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE,responseCookie.toString());
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }
}
