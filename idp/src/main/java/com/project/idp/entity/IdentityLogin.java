package com.project.idp.entity;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IdentityLogin {
    private String username;

    private String password;
}
