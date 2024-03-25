package com.project.idp.service;

import com.project.idp.dto.IdentityResponse;
import com.project.idp.dto.TokenResponse;
import com.project.idp.entity.Identity;
import com.project.idp.entity.IdentityLogin;
import com.project.idp.exception.CredentialsNotFoundException;
import com.project.idp.filter.JwtUtils;
import com.project.idp.interfaces.IdentityInterface;
import com.project.idp.repository.IdentityRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IdentityService implements IdentityInterface {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public IdentityResponse register(Identity identityRegister) {
        Identity identity= Identity.builder().id(identityRegister.getId())
                .firstname(identityRegister.getFirstname())
                .lastname(identityRegister.getLastname())
                .roles(identityRegister.getRoles())
                .password(passwordEncoder.encode(identityRegister.getPassword()))
                .email(identityRegister.getEmail())
                .build();
        identityRepository.save(identity);
        return new IdentityResponse(identityRegister.getId(),identityRegister.getFirstname(),identityRegister.getLastname());
    }

    @Override
    public TokenResponse login(IdentityLogin identityLogin) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                identityLogin.getUsername(),
                identityLogin.getPassword()
        ));
        Identity identity= identityRepository.findByEmail(identityLogin.getUsername())
                .orElseThrow(()-> new CredentialsNotFoundException("Wrong Credentials"));
        String token=jwtUtils.generateToken(identity.getUsername(),identity.getRoles(),identity.getRoles().getPermissions());
        return new TokenResponse(token);
    }
}
