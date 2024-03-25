package com.project.idp.filter;

import com.project.idp.enums.Permissions;
import com.project.idp.enums.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    private Key getSigningKey()
    {
        byte[] keys= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keys);
    }

    public String generateToken(String username, Roles roles, List<Permissions> permissions)
    {
        Claims claims=Jwts.claims().setSubject(username);
        claims.put("roles",roles);
        claims.put("permission",permissions.stream().map(Enum::name).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
