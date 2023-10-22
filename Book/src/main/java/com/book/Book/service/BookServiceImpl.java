package com.book.Book.service;

import com.book.Book.model.BookEntity;
import com.book.Book.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(final BookRepo bookRepo){
        this.bookRepo=bookRepo;
    }
    @Override
    public BookEntity create(BookEntity book) {
        return bookRepo.save(book);
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Optional<BookEntity> getBookById(String isbn) {
        return bookRepo.findById(isbn);
    }

    @Override
    public boolean isBookExist(BookEntity book) {
        return bookRepo.existsById(book.getIsbn());
    }
}
