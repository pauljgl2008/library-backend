package com.growby.library.backend.service.impl;

import com.growby.library.backend.common.ValidationConstants;
import com.growby.library.backend.exception.BookNotFoundException;
import com.growby.library.backend.exception.InvalidFieldException;
import com.growby.library.backend.mapper.AuthorEntityMapper;
import com.growby.library.backend.model.dto.author.AuthorRequestDto;
import com.growby.library.backend.model.dto.author.AuthorResponseDto;
import com.growby.library.backend.model.entity.Author;
import com.growby.library.backend.repository.author.impl.AuthorRepositoryImpl;
import com.growby.library.backend.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorEntityMapper authorEntityMapper;
    private final AuthorRepositoryImpl authorRepository;

    @Override
    public boolean isAuthorExists(Long id) {
        return this.authorRepository.isAuthorExists(id);
    }

    @Override
    public List<AuthorResponseDto> getAllAuthors() {
        List<Author> authors = this.authorRepository.findAll();
        return this.authorEntityMapper.authorListToAuthorResponseDtoList(authors);
    }

    @Override
    public Optional<AuthorResponseDto> getAuthorById(Long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        return author.map(this.authorEntityMapper::toAuthorResponseDto);
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
        if (this.authorRepository.existsByName(authorRequestDto.getName())) {
            throw new InvalidFieldException(HttpStatus.BAD_REQUEST, "name", authorRequestDto.getName(),
                    ValidationConstants.AUTHOR_NAME_EXISTS_MESSAGE);
        }

        Author author = this.authorEntityMapper.fromAuthorRequestDto(authorRequestDto);
        author = this.authorRepository.save(author);
        return this.authorEntityMapper.toAuthorResponseDto(author);
    }

    @Override
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto authorRequestDto) {
        Author author = this.authorRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(HttpStatus.BAD_REQUEST, "id", id.toString(),
                        ValidationConstants.AUTHOR_NOT_FOUND_MESSAGE));
        author.setId(authorRequestDto.getId());
        author.setName(authorRequestDto.getName());
        author.setBirthDate(LocalDate.parse(authorRequestDto.getBirthDate()));
        author.setNationality(authorRequestDto.getNationality());
        author = this.authorRepository.save(author);
        return this.authorEntityMapper.toAuthorResponseDto(author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        this.authorRepository.deleteById(id);
    }
}
