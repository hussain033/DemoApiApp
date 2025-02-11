package com.example.consoleApp.model;

// the response for jwt is jwt token
public class AuthenticationResponse {
    final private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
