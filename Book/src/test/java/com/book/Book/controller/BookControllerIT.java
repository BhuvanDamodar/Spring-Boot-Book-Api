package com.book.Book.controller;


import com.book.Book.TestData;
import com.book.Book.model.BookEntity;
import com.book.Book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //clears database after each test
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testThatBookIsCreated() throws Exception {
        final BookEntity book = TestData.testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/book").content(bookJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.book_name").value(book.getBook_name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.book_author").value(book.getBook_author()));
    }

    @Test
    public void testForBookAlreadyExists() throws Exception {
        final BookEntity book = TestData.testBook();
        bookService.create(book);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/add/book").content(bookJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.book_name").value(book.getBook_name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.book_author").value(book.getBook_author()));
    }

    @Test
    public void testForBookNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/121233434"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testForBookFound() throws Exception{
        final BookEntity book = TestData.testBook();
        bookService.create(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/"+ book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.book_name").value(book.getBook_name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.book_author").value(book.getBook_author()));
    }

    @Test
    public void testThatFindAllHasNoBooks() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatFindAllHasBooks() throws Exception{
        final BookEntity book = TestData.testBook();
        bookService.create(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].book_name").value(book.getBook_name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].book_author").value(book.getBook_author()));

    }

}