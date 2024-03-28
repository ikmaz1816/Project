package com.project.employeeleave.filters;

import com.project.employeeleave.exception.TokenNotValidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secret;
    public String extractUsername(String auth) {
        return extractClaim(auth, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claim = extractAllClaims(token);
        return claimResolver.apply(claim);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws TokenNotValidException {
        String userName = extractUsername(token);
        if(!userName.equals(userDetails.getUsername()) && !isTokenExpired(token)){
            throw new TokenNotValidException("Invalid Token!!");
        }
        return true;
    }

    private boolean isTokenExpired(String token) {
        return extractDate(token).before(new Date());
    }

    private Date extractDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        String role = claims.get("role", String.class);
        List<String> permissions = claims.get("permission", List.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(role != null){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        if(permissions != null){
            authorities.addAll(permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        return authorities;
    }
}
