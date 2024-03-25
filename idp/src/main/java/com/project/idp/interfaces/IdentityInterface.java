package com.project.idp.interfaces;

import com.project.idp.dto.IdentityResponse;
import com.project.idp.dto.TokenResponse;
import com.project.idp.entity.Identity;
import com.project.idp.entity.IdentityLogin;
import org.springframework.stereotype.Service;

@Service
public interface IdentityInterface
{
    IdentityResponse register(Identity identityRegister);

    TokenResponse login(IdentityLogin identityLogin);
}
