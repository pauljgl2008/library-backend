package com.growby.library.backend.service.impl;

import com.growby.library.backend.common.ValidationConstants;
import com.growby.library.backend.exception.BookNotFoundException;
import com.growby.library.backend.exception.InvalidFieldException;
import com.growby.library.backend.mapper.BookEntityMapper;
import com.growby.library.backend.model.dto.book.BookRequestDto;
import com.growby.library.backend.model.dto.book.BookResponseDto;
import com.growby.library.backend.model.entity.Book;
import com.growby.library.backend.repository.author.AuthorJpaRepository;
import com.growby.library.backend.repository.book.BookRepository;
import com.growby.library.backend.service.BookService;
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
    private final AuthorJpaRepository authorJpaRepository;

    @Override
    public boolean isBookAvailable(Long id) {
        return this.bookRepository.isBookAvailable(id);
    }

    @Override
    public Page<BookResponseDto> getBooksWithPagination(Pageable pageable, String title, String author) {
        Page<Book> bookPage = this.bookRepository.findAllWithPagination(pageable, title, author);

        List<BookResponseDto> bookResponseDtos = this.bookEntityMapper.bookListToBookResponseDtoList(bookPage.getContent());

        return new PageImpl<>(bookResponseDtos, pageable, bookPage.getTotalElements());
    }


    @Override
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
        if (this.bookRepository.existsByIsbn(bookRequestDto.getIsbn())) {
            throw new InvalidFieldException(HttpStatus.BAD_REQUEST, "isbn", bookRequestDto.getIsbn(),
                    ValidationConstants.BOOK_ISBN_EXISTS_MESSAGE);
        }

        Book book = this.bookEntityMapper.fromBookRequestDto(bookRequestDto);
        book = this.bookRepository.save(book);
        return this.bookEntityMapper.toBookResponseDto(book);
    }

    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(HttpStatus.BAD_REQUEST, "id", id.toString(),
                        ValidationConstants.BOOK_NOT_FOUND_MESSAGE));

        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setAuthor(this.authorJpaRepository.findById(bookRequestDto.getAuthor())
                .orElseThrow(() -> new BookNotFoundException(HttpStatus.BAD_REQUEST, "id",
                        bookRequestDto.getAuthor().toString(), ValidationConstants.BOOK_NOT_FOUND_MESSAGE)));
        book.setPublicationDate(bookRequestDto.getPublicationDate());
        book.setStatus(bookRequestDto.getStatus());

        book = this.bookRepository.save(book);
        return this.bookEntityMapper.toBookResponseDto(book);
    }

    @Override
    public Optional<BookResponseDto> getBookById(Long bookId) {
        Optional<Book> book = this.bookRepository.findById(bookId);
        return book.map(this.bookEntityMapper::toBookResponseDto);
    }

    @Override
    public void deleteBookById(Long id) {
        this.bookRepository.deleteById(id);
    }
}
