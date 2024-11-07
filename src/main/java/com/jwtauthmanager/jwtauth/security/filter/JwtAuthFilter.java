package com.jwtauthmanager.jwtauth.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtauthmanager.jwtauth.models.userdetail.UserDetailslImpl;
import com.jwtauthmanager.jwtauth.services.UserDetailsServiceImpl;
import com.jwtauthmanager.jwtauth.utils.JwtTokenManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    final JwtTokenManager jwtTokenManager;

    final UserDetailsServiceImpl userDetailsServiceImpl;

    JwtAuthFilter(JwtTokenManager jwtTokenManager,UserDetailsServiceImpl userDetailsServiceImpl){
        this.jwtTokenManager = jwtTokenManager;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String tokenFromHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(tokenFromHeader != null && tokenFromHeader.startsWith("Bearer ")){
            token = tokenFromHeader.substring(7).trim();
            if(jwtTokenManager.validateToken(token)){
                username = jwtTokenManager.getUserNameFromJwt(token);
                UserDetailslImpl userDetailslImpl = (UserDetailslImpl) userDetailsServiceImpl.loadUserByUsername(username);
                if(userDetailslImpl != null){
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null , userDetailslImpl.getAuthorities());
                    auth.setDetails(userDetailslImpl);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
}
