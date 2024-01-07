package org.afecioru.study.lsb3.ch5.controllers;

import org.afecioru.study.lsb3.ch5.controllers.dtos.BookDTO;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.afecioru.study.lsb3.ch5.services.BookService;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @Test
    @WithMockUser
    void indexPageHasAddForm() throws Exception {
        // Arrange - nothing to do

        // Act
        var action = mvc.perform(get("/"));
        var html = action.andReturn().getResponse().getContentAsString();

        // Assert
        action.andExpect(status().isOk());
        action.andExpect(content().string(containsString("Chapter 5")));

        assertThat(html).contains(
            "<form method=\"POST\" action=\"/add\""
        );
    }

    @Test
    @WithMockUser
    void addNewBook() throws Exception {
        // Arrange - nothing to do

        // Act
        var action = mvc.perform(post("/add")
            .param("title", "New book")
            .param("author", "New Author")
            .param("copiesSold", "100")
            .with(csrf())
        );

        // Assert
        action
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

        verify(bookService).addBook(
            new BookDTO(null, "New book", "New Author", 100)
        );
    }

    @Test
    @WithMockUser
    void deleteBook() throws Exception {
        // Arrange - nothing to do

        // Act
        var action = mvc.perform(post("/delete/5").with(csrf()));

        // Assert
        action
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
        verify(bookService).deleteBook(5L);
    }

}
