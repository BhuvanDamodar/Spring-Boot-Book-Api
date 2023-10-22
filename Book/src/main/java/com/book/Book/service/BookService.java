package com.book.Book.service;

import com.book.Book.model.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity create(BookEntity book);

    List<BookEntity> getAllBooks();

    Optional<BookEntity> getBookById(String isbn);

    boolean isBookExist(BookEntity book);
}
