package com.growby.library.backend.service;

import com.growby.library.backend.common.ValidationConstants;
import com.growby.library.backend.exception.BookNotFoundException;
import com.growby.library.backend.mapper.BookEntityMapper;
import com.growby.library.backend.model.dto.book.BookRequestDto;
import com.growby.library.backend.model.dto.book.BookResponseDto;
import com.growby.library.backend.model.entity.Book;
import com.growby.library.backend.model.entity.LoanStatus;
import com.growby.library.backend.repository.BookRepository;
import com.growby.library.backend.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class BookServiceImpl implements BookService {
    private final BookEntityMapper bookEntityMapper;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Override
    public List<BookResponseDto> getBooks() {
        List<Book> books = this.bookRepository.findAll();
        return this.bookEntityMapper.bookListToBookResponseDtoList(books);
    }

    @Override
    public List<BookResponseDto> getBooksWithPagination(int page, int size) {
        Page<Book> booksPage = bookRepository.findAll(PageRequest.of(page, size));
        return this.bookEntityMapper.bookListToBookResponseDtoList(booksPage.getContent());
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
                        ValidationConstants.BOOK_NOT_EXISTS_MESSAGE));

        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublicationDate(bookRequestDto.getPublicationDate());
        book.setStatus(bookRequestDto.getStatus());

        book = bookRepository.save(book);
        return bookEntityMapper.toBookResponseDto(book);
    }

    @Override
    public boolean isBookAvailable(Long bookId) {
        // Obtener el libro, o lanzar una excepción si no existe
        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(HttpStatus.BAD_REQUEST, "id", bookId.toString(),
                        ValidationConstants.BOOK_NOT_EXISTS_MESSAGE));

        // Verificar si el libro tiene préstamos activos
        long activeLoansCount = this.loanRepository.countByBookAndStatus(book, LoanStatus.ACTIVE);

        // Si tiene préstamos activos, no está disponible
        return activeLoansCount == 0;
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(HttpStatus.BAD_REQUEST, "id", id.toString(),
                    ValidationConstants.BOOK_NOT_EXISTS_MESSAGE);
        }
        this.bookRepository.deleteById(id);
    }
}
