package com.growby.library.backend.service;

import com.growby.library.backend.common.ValidationConstants;
import com.growby.library.backend.exception.BookNotFoundException;
import com.growby.library.backend.mapper.BookEntityMapper;
import com.growby.library.backend.model.dto.book.BookRequestDto;
import com.growby.library.backend.model.dto.book.BookResponseDto;
import com.growby.library.backend.model.entity.Book;
import com.growby.library.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class BookServiceImpl implements BookService {
    private final BookEntityMapper bookEntityMapper;
    private final BookRepository bookRepository;

    @Override
    public boolean isBookAvailable(Long id) {
        return bookRepository.isBookAvailable(id);
    }

    @Override
    public Page<BookResponseDto> getBooksWithPagination(Pageable pageable, String title, String author) {
        Page<Book> bookPage = bookRepository.findAllWithPagination(pageable, title, author);

        List<BookResponseDto> bookResponseDtos = bookEntityMapper.bookListToBookResponseDtoList(bookPage.getContent());

        return new PageImpl<>(bookResponseDtos, pageable, bookPage.getTotalElements());
    }


    @Override
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
        Book book = this.bookEntityMapper.fromBookRequestDto(bookRequestDto);
        book = this.bookRepository.save(book);
        return this.bookEntityMapper.toBookResponseDto(book);
    }

    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(HttpStatus.BAD_REQUEST, "id", id.toString(),
                        ValidationConstants.BOOK_NOT_FOUND_MESSAGE));

        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublicationDate(bookRequestDto.getPublicationDate());
        book.setStatus(bookRequestDto.getStatus());

        book = bookRepository.save(book);
        return bookEntityMapper.toBookResponseDto(book);
    }

    @Override
    public Optional<BookResponseDto> getBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(this.bookEntityMapper::toBookResponseDto);
    }

    @Override
    public void deleteBook(Long id) {
        this.bookRepository.deleteById(id);
    }
}
