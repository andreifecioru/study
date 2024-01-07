package org.afecioru.study.lsb3.ch5.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.afecioru.study.lsb3.ch5.services.BookService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerSecurityTest {

    @Autowired
    private WebApplicationContext appContext;

    @MockBean
    BookService bookService;

    private MockMvc mvc;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders
            .webAppContextSetup(appContext)
            .dispatchOptions(true)
            .apply(springSecurity())
            .build();
    }

    @Test
    void unauthorizedAccessForNonAuthenticatedUser() throws Exception {
        // Arrange - nothing to do

        // Act
        var action = mvc.perform(get("/"));

        // Assert
        action.andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "guest", roles = "USER")
    void unauthorizedAccessForNonAdminUser() throws Exception {
        // Arrange - nothing to do

        // Act
        var action = mvc.perform(post("/delete/5").with(csrf()));

        // Assert
        action.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void authorizedAccessForAdminUser() throws Exception {
        // Arrange - nothing to do

        // Act
        var action = mvc.perform(post("/delete/5").with(csrf()));

        // Assert
        action.andExpect(status().is3xxRedirection());
    }
}
