package com.example.marketplace.config;

import com.example.marketplace.exception.ForbiddenException;
import com.example.marketplace.service.AccountService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.io.IOException;
import java.util.Map;

public class ValidationFilter implements Filter {
    @Autowired
    AccountService accountService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof JwtAuthenticationToken) {
            Jwt token= (Jwt) authentication.getPrincipal();
            Map claims =token.getClaims();
            Long id = (Long) claims.get("id");
            Long modifiedOnMillis = (Long) claims.get("modifiedon");
            if(!accountService.verifyAccount(id,modifiedOnMillis)){
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        chain.doFilter(request,response);
    }
}
