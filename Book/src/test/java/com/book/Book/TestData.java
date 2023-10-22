package com.book.Book;

import com.book.Book.model.BookEntity;

public class TestData {

    private TestData(){}

    public static BookEntity testBook(){
        return BookEntity.builder()
                .isbn("37363")
                .book_name("The water")
                .book_author("Mark")
                .build();
    }

    public static BookEntity testBook2(){
        return BookEntity.builder()
                .isbn("37483")
                .book_name("The land")
                .book_author("Sam")
                .build();
    }
}
