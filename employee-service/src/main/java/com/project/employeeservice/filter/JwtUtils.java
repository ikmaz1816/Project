package com.project.employeeservice.filter;

import com.project.employeeservice.exception.TokenNotValidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils
{
    @Value("${jwt.secret.key}")
    private String secret;

    public String extractSubject(String token)
    {
        return getClaim(Claims::getSubject,token);
    }

    private Date extractDate(String token)
    {
        return getClaim(Claims::getExpiration,token);
    }

    public List<GrantedAuthority> extractAuthorities(String token)
    {
        Claims claims=getAllClaims(token);
        String role = claims.get("roles",String.class);
        List<String> permissions = claims.get("permission",List.class);
        List<GrantedAuthority> authorities=new ArrayList<>();
        if(role!=null)
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        if(permissions!=null)
        {
            authorities.addAll(permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        return authorities;
    }

    private <T> T getClaim(Function<Claims,T> claimResolver, String token)
    {
        Claims claims=getAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey()
    {
        byte[] keys= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keys);
    }

    public boolean isTokenValid(UserDetails userDetails,String token) throws TokenNotValidException {
        String username=extractSubject(token);
        if(!(username.equals(userDetails.getUsername()) && !isExpired(token)))
        {
            throw new TokenNotValidException("Token is not valid");
        }
        return true;
    }

    private boolean isExpired(String token)
    {
        return extractDate(token).before(new Date());
    }
}
