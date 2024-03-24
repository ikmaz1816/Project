package com.project.employeeservice.filter;

import com.project.employeeservice.exception.TokenNotValidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

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
