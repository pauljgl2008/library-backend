package com.growby.library.backend.service;

import com.growby.library.backend.model.dto.author.AuthorRequestDto;
import com.growby.library.backend.model.dto.author.AuthorResponseDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<AuthorResponseDto> getAuthorById(Long bookId);

    List<AuthorResponseDto> getAuthors();

    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);

    AuthorResponseDto updateAuthor(Long id, AuthorRequestDto authorRequestDto);

    void deleteAuthorById(Long id);
}

