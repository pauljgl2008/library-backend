package com.growby.library.backend.service;

import com.growby.library.backend.model.dto.book.BookRequestDto;
import com.growby.library.backend.model.dto.book.BookResponseDto;
import com.growby.library.backend.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {

    Optional<BookResponseDto> getBookById(Long bookId);

    Page<BookResponseDto> getBooksWithPagination(Pageable pageable, String title, String author);

    BookResponseDto createBook(BookRequestDto bookRequestDto);

    BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto);

    boolean isBookAvailable(Long bookId);

    void deleteBook(Long id);
}

