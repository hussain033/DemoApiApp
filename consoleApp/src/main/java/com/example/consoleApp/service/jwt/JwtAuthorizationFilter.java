package com.example.consoleApp.service.jwt;

import com.example.consoleApp.service.exception.InvalidJwtTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.tool.schema.internal.ExceptionHandlerLoggedImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    Logger logger = LoggerFactory.getLogger(ExceptionHandlerLoggedImpl.class);


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        // For users without tokens
        if (Objects.isNull(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        // for users with token
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

            // sets the details of currently authenticated user into the context holder
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException e) {
          throw new RuntimeException("expired token");
        } catch (SignatureException e) {
            throw new InvalidJwtTokenException("invalid token");
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed");
        }
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws RuntimeException {
        String token = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authentication = null;

        if (Objects.nonNull(token)) {
                String username = jwtUtil.extractUsernameFromToken(token.replace("Bearer ", ""));
                if (Objects.nonNull(username)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtil.validateToken(token.replace("Bearer ", ""), userDetails)) {
                        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    }
                }

        }
        return authentication;
    }
}
