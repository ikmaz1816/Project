package com.project.idp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.idp.enums.Permissions.*;

@RequiredArgsConstructor
public enum Roles {
    USER(
            List.of(
            USER_READ,
            USER_CREATE,
            USER_DELETE,
            USER_UPDATE
        )
    ),
    ADMIN(
            List.of(
                    USER_READ,
                    USER_CREATE,
                    USER_DELETE,
                    USER_UPDATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE
            )
    );

    @Getter
    private final List<Permissions> permissions;

    public List<SimpleGrantedAuthority> simpleGrantedAuthorities()
    {
        List<SimpleGrantedAuthority> authorities=getPermissions().stream()
                .map(permission-> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
