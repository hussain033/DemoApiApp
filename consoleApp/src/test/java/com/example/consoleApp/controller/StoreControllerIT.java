package com.example.consoleApp.controller;

import com.example.consoleApp.model.AdminAcc;
import com.example.consoleApp.model.AuthenticationRequest;
import com.example.consoleApp.model.Item;
import com.example.consoleApp.model.UserAcc;
import com.example.consoleApp.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StoreControllerIT {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {

        Item item = new Item("item1", 10);
        Item item2 = new Item("item2", 20);
        Item item3 = new Item("item3", 30);

        itemRepository.saveAll(List.of(item, item2, item3));

    }

    @AfterEach
    void tearDown() {

        itemRepository.deleteAll();
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void listItem() throws Exception {

        // when
        RequestBuilder request = get("/store/item");

        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", hasSize(3)));

    }

    @Test
    void listItemThrowUnauthorized() throws Exception {

        RequestBuilder request = get("/store/item");

        mvc.perform(request).
                andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void addItem() throws Exception {
        // given
        Long Id = 4L;
        Item item = new Item("Item4", 40);
        String itemJson = objectMapper.writeValueAsString(item);
        RequestBuilder request = post("/store/item").contentType(MediaType.APPLICATION_JSON) // Important!
                .content(itemJson).with(csrf());


        // when
        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();


      assertThat(Integer.valueOf(result.getResponse().getContentAsString())).isInstanceOf(Integer.class);

    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void deleteItemById() throws Exception {
        // given
        Long id = itemRepository.findAll().getFirst().getId();
        RequestBuilder request = delete("/store/item/" + id).with(csrf());

        // when
        mvc.perform(request).andExpect(status().isOk());


        // then
        assertThat(itemRepository.findById(id)).isEmpty();

    }

    @Test
    void createAuthenticationToken() throws Exception {
        // given
        String username = "user1";
        String password = "pass1";
        AuthenticationRequest authReq = new AuthenticationRequest(username, password);
        String authJson = objectMapper.writeValueAsString(authReq);
        RequestBuilder request = post("/acc/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authJson).with(csrf());

        // when and then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value(any(String.class)));


    }

    @Test
    void saveUser() throws Exception {
        // given
        String username = "user1";
        String password = "pass1";
        UserAcc authRequest = new UserAcc(null, username, password);
        String accJson = objectMapper.writeValueAsString(authRequest);
        RequestBuilder request = post("/acc/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accJson).with(csrf());

        // when and then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(any(Integer.class)))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.password").value("******"));
    }

    @Test
    void saveAdmin() throws Exception{
        // given
        String username = "admin1";
        String password = "pass1";
        AdminAcc authRequest = new AdminAcc(null, username, password);
        String accJson = objectMapper.writeValueAsString(authRequest);
        RequestBuilder request = post("/acc/admin/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accJson).with(csrf());

        // when and then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(any(Integer.class)))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.password").value("******"));
    }
}