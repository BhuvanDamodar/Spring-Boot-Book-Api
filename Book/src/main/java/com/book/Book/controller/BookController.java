package com.book.Book.controller;

import com.book.Book.model.BookEntity;
import com.book.Book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService=bookService;
    }

    @PostMapping("/add/book")
    public ResponseEntity<BookEntity> create(@RequestBody BookEntity book){
        final boolean result = bookService.isBookExist(book);
        bookService.create(book);
        ResponseEntity<BookEntity> response;
        if(result){
            response = new ResponseEntity<>(book, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(book, HttpStatus.CREATED);
        }
        return response;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookEntity>> getAllBooks(){
        List<BookEntity> books = bookService.getAllBooks();
        ResponseEntity<List<BookEntity>> response = new ResponseEntity<>(books, HttpStatus.OK);
        return response;
    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable String isbn){
        Optional<BookEntity> foundBook = bookService.getBookById(isbn);
        return foundBook.map(book -> new ResponseEntity<BookEntity>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<BookEntity>(HttpStatus.NOT_FOUND));
    }
}
