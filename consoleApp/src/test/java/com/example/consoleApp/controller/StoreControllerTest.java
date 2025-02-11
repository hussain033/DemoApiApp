package com.example.consoleApp.controller;

import com.example.consoleApp.model.Item;
import com.example.consoleApp.service.StoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StoreController.class)
@AutoConfigureMockMvc
class StoreControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private StoreService storeService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setup() {
        // mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Item item = new Item("item1", 10);
        Item item2 = new Item("item2", 20);
        Item item3 = new Item("item3", 30);

        when(storeService.listItem()).thenReturn(List.of(item, item2, item3));
    }

    @AfterEach
    void tearDown() {
        storeService.deleteAll();
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void listItem() throws Exception {

        // when
        RequestBuilder request = get("/store/item");

        // then
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.[*]", hasSize(3)));

    }

    @Test
    void listItemThrowUnauthorized() throws Exception {

        RequestBuilder request = get("/store/item");

        mvc.perform(request).
                andExpect(MockMvcResultMatchers.status().isUnauthorized());
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
        when(storeService.addItem(any(Item.class))).thenReturn(Id);


        // when
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(String.valueOf(Id));

    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void deleteItemById() throws Exception {
        // given
        Long id = 1L;
        RequestBuilder request = delete("/store/item/" + String.valueOf(id)).with(csrf());

        // when
        mvc.perform(request);


        // then
        assertThat(storeService.getItemById(id)).isNull();

    }
}