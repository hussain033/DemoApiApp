package com.example.consoleApp.controller;

import com.example.consoleApp.model.AdminAcc;
import com.example.consoleApp.model.AuthenticationRequest;
import com.example.consoleApp.model.AuthenticationResponse;
import com.example.consoleApp.model.UserAcc;
import com.example.consoleApp.service.AccService;
import com.example.consoleApp.service.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/acc")
public class AccController {

    Logger logger = LoggerFactory.getLogger("");

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    AccService accService;


    public AccController(AccService accService) {

        this.accService = accService;
    }

    @GetMapping("/user/{id}")
    public UserAcc getUserById(@PathVariable("id") Long id) {

        return accService.getUserById(id);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {

            logger.error("Incorrect credentials");

            throw new BadCredentialsException("Incorrect Username or Password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);


        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserAcc> saveUser(@RequestBody UserAcc userDto) {
        if (Objects.isNull(userDto)) {
            throw new RuntimeException("Payload cannot be Null");
        }
        if(accService.findUserByUsername(userDto.getUsername()) != null){
            throw new RuntimeException("Username is already taken");
        }
        return ResponseEntity.ok(accService.addUser(userDto));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AdminAcc> saveAdmin(@RequestBody AdminAcc adminAcc) {
        if (Objects.isNull(adminAcc)) {
            throw new RuntimeException("Payload cannot be Null");
        }
        if(accService.findAdminByUsername(adminAcc.getUsername()) != null){
            throw new RuntimeException("Username is already taken");
        }
        return ResponseEntity.ok(accService.addAdmin(adminAcc));
    }


    @GetMapping("/admin/{id}")
    public AdminAcc getAdminById(@PathVariable("id") Long id) {

        return accService.getAdminById(id);
    }
}
