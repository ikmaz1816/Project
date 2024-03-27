package com.project.employeeleave.filters;

import com.project.employeeleave.exception.TokenNotValidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = null;
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("accessToken")){
                    auth = cookie.getValue();
                }
            }
        }
        if(auth == null){
            filterChain.doFilter(request, response);
            return;
        }

        String user = jwtUtils.extractUsername(auth);

        if(user != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(user);
            try {
                if(jwtUtils.isTokenValid(auth, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            "",
                            jwtUtils.extractAuthorities(auth)
                    );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (TokenNotValidException e) {
                throw new TokenNotValidException("Token is not valid");
            }
        }

        filterChain.doFilter(request, response);
    }
}
