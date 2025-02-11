package com.example.consoleApp.controller;

import com.example.consoleApp.model.AdminAcc;
import com.example.consoleApp.model.AuthenticationRequest;
import com.example.consoleApp.model.UserAcc;
import com.example.consoleApp.service.AccService;
import com.example.consoleApp.service.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    AccService accService;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    JwtUtil jwtUtil;

    @MockitoBean
    UserDetailsService userDetailsService;

    @MockitoBean
    AuthenticationManager authenticationManager;

    @BeforeEach
    void setup() {
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createAuthenticationToken() throws Exception{
        // given
        String username = "user1";
        String password = "pass1";
        AuthenticationRequest authReq = new AuthenticationRequest(username, password);
        String authJson = objectMapper.writeValueAsString(authReq);
        RequestBuilder request = post("/acc/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authJson).with(csrf());

        // when and then
        UserDetails user = new User(username, password, Collections.emptyList());
        when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("adfasfs");

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value(Matchers.any(String.class)));


    }

    @Test
    void saveUser() throws Exception{
        // given
        String username = "user1";
        String password = "pass1";
        UserAcc authRequest = new UserAcc(null, username, password);
        String accJson = objectMapper.writeValueAsString(authRequest);
        RequestBuilder request = post("/acc/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accJson).with(csrf());

        // when and then
        UserAcc savedUser = new UserAcc(1L, username, "******");
        when(accService.addUser(any(UserAcc.class))).thenReturn(savedUser);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Matchers.any(Integer.class)))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.password").value("******"));

        //System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    void saveAdmin() throws Exception{
        // given
        String username = "user1";
        String password = "pass1";
        AdminAcc authRequest = new AdminAcc(null, username, password);
        String accJson = objectMapper.writeValueAsString(authRequest);
        RequestBuilder request = post("/acc/admin/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accJson).with(csrf());

        // when and then
        AdminAcc savedUser = new AdminAcc(1L, username, "******");
        when(accService.addAdmin(any(AdminAcc.class))).thenReturn(savedUser);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Matchers.any(Integer.class)))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.password").value("******"));
    }

}