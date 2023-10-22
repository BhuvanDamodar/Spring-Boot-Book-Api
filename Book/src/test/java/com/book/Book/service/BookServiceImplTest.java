package com.book.Book.service;

import com.book.Book.TestData;
import com.book.Book.model.BookEntity;
import com.book.Book.repository.BookRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest{
    @Mock
    private BookRepo bookRepo;
    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved(){
        final BookEntity book = TestData.testBook();
        final BookEntity book1 = TestData.testBook2();

        when(bookRepo.save(eq(book))).thenReturn(book);

        final BookEntity result = underTest.create(book);

        assertEquals(book, result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook(){
        final String isbn = "1223455";
        when(bookRepo.findById(eq(isbn))).thenReturn(Optional.empty());
        final Optional<BookEntity> result = underTest.getBookById(isbn);
        assertEquals(Optional.empty(),result);
    }

    @Test
    public void testThatFindByIdReturnBookWhenExist(){
        final BookEntity book = TestData.testBook();

        when(bookRepo.findById(eq(book.getIsbn()))).thenReturn(Optional.of(book));
        final Optional<BookEntity> result = underTest.getBookById(book.getIsbn());
        assertEquals(Optional.of(book),result);
    }

    @Test
    public void testThatFindAllHasNoBooks(){
        when(bookRepo.findAll()).thenReturn(new ArrayList<BookEntity>());
        final List<BookEntity> result = underTest.getAllBooks();
        assertEquals(0, result.size());
    }

    @Test
    public void testThatFindAllHasBooks(){
        final BookEntity book = TestData.testBook();
        when(bookRepo.findAll()).thenReturn(List.of(book));
        final List<BookEntity> result = underTest.getAllBooks();
        assertEquals(1, result.size());
    }

    @Test
    public void testForBookDoesNotExist(){
        when(bookRepo.existsById(any())).thenReturn(false);
        final boolean result = underTest.isBookExist(TestData.testBook());
        assertEquals(false,result);
    }

    @Test
    public void testForBookExists(){
        final BookEntity book = TestData.testBook();

        when(bookRepo.existsById(any())).thenReturn(true);
        final boolean result = underTest.isBookExist(book);
        assertEquals(true,result);

    }
}
