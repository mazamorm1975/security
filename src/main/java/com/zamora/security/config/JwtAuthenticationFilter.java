package com.zamora.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String headerAuthenticationToken = request.getHeader("Authorization");
        String jwtToken = null;
        String userEmail = null;

        if (headerAuthenticationToken == null || !headerAuthenticationToken.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

            jwtToken = headerAuthenticationToken.substring(7);
            userEmail = jwtService.extractUsername(jwtToken);

            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication()== null){

                UserDetails authToken = this.userDetailsService.loadUserByUsername(userEmail);

                if(jwtService.isTokenValid(jwtToken, authToken)){

                    UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(
                            authToken, null, authToken.getAuthorities());
                userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userAuthToken);

            }

            filterChain.doFilter(request, response);
        }

    }

}
