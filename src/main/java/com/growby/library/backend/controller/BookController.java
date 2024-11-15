package com.growby.library.backend.controller;

import com.growby.library.backend.model.dto.book.BookRequestDto;
import com.growby.library.backend.model.dto.book.BookResponseDto;
import com.growby.library.backend.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.growby.library.backend.common.ValidationConstants.BOOK_STATUS_AVAILABLE_MESSAGE;
import static com.growby.library.backend.common.ValidationConstants.BOOK_STATUS_NOT_AVAILABLE_MESSAGE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookResponseDto>> getBooksWithPagination(Pageable pageable,
                                                                        @RequestParam(required = false) String title,
                                                                        @RequestParam(required = false) String author) {
        Page<BookResponseDto> books = this.bookService.getBooksWithPagination(pageable, title, author);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookCreated = this.bookService.createBook(bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookCreated);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<String> checkBookAvailability(@PathVariable Long id) {
        boolean isAvailable = this.bookService.isBookAvailable(id);
        if (isAvailable) {
            return ResponseEntity.ok(BOOK_STATUS_AVAILABLE_MESSAGE);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BOOK_STATUS_NOT_AVAILABLE_MESSAGE);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        return this.bookService.getBookById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto updatedBook = this.bookService.updateBook(id, bookRequestDto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
