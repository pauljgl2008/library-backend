package com.growby.library.backend.service;

import com.growby.library.backend.model.dto.book.BookRequestDto;
import com.growby.library.backend.model.dto.book.BookResponseDto;

import java.util.List;

public interface BookService {

    List<BookResponseDto> getBooks();

    List<BookResponseDto> getBooksWithPagination(int page, int size);

    BookResponseDto createBook(BookRequestDto bookRequestDto);

    BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto);

    boolean isBookAvailable(Long bookId);

    void deleteBook(Long id);
}

