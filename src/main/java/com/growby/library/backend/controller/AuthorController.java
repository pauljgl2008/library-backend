package com.growby.library.backend.controller;

import com.growby.library.backend.model.dto.author.AuthorRequestDto;
import com.growby.library.backend.model.dto.author.AuthorResponseDto;
import com.growby.library.backend.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.growby.library.backend.common.ValidationConstants.BOOK_STATUS_AVAILABLE_MESSAGE;
import static com.growby.library.backend.common.ValidationConstants.BOOK_STATUS_NOT_AVAILABLE_MESSAGE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAll() {
        List<AuthorResponseDto> authors = this.authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@Valid @RequestBody AuthorRequestDto authorRequestDto) {
        AuthorResponseDto authorCreated = this.authorService.createAuthor(authorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorCreated);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<String> checkBookAvailability(@PathVariable Long id) {
        boolean isAvailable = this.authorService.isAuthorExists(id);
        if (isAvailable) {
            return ResponseEntity.ok(BOOK_STATUS_AVAILABLE_MESSAGE);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BOOK_STATUS_NOT_AVAILABLE_MESSAGE);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getBookById(@PathVariable Long id) {
        return this.authorService.getAuthorById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequestDto authorRequestDto) {
        AuthorResponseDto updatedBook = this.authorService.updateAuthor(id, authorRequestDto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        this.authorService.deleteAuthorById(id);
        return ResponseEntity.noContent().build();
    }
}
