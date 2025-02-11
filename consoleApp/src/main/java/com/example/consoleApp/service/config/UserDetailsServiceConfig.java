package com.example.consoleApp.service.config;

import com.example.consoleApp.model.AdminAcc;
import com.example.consoleApp.model.UserAcc;
import com.example.consoleApp.repository.UserAccRepository;
import com.example.consoleApp.repository.AdminAccRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Optional;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(UserAccRepository userRepository, AdminAccRepository adminRepository) {
        return username -> {
            Optional<UserAcc> user = userRepository.findByUsername(username);
            Optional<AdminAcc> admin = adminRepository.findByUsername(username);
            if(user.isPresent()) {
                return new User(user.get().getUsername(), user.get().getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
            } else if(admin.isPresent()) {
                return new User(admin.get().getUsername(), admin.get().getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
            throw new UsernameNotFoundException("User or Admin Acc not found");

        };
    }
}
