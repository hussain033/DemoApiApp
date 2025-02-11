package com.example.consoleApp.service.config;

import com.example.consoleApp.service.jwt.JwtAuthorizationFilter;
import com.example.consoleApp.service.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;


    public SecurityConfiguration() {
    }

    @Bean
    public SecurityFilterChain filter (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.GET, "/store/item").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/store/item/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/store/item").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/store/item/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/acc/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/acc/user/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/acc/admin/register").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated())
        .csrf(crsf -> crsf.disable())
        .cors(cors -> cors.configure(httpSecurity))
        .exceptionHandling(exception -> exception
        .authenticationEntryPoint(customBasicAuthenticationEntryPoint))
        .addFilter(new JwtAuthorizationFilter(authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }





}
