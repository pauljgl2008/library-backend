package com.growby.library.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.growby.library.backend.model.dto.author.AuthorRequestDto;
import com.growby.library.backend.model.dto.author.AuthorResponseDto;
import com.growby.library.backend.service.AuthorService;
import com.growby.library.backend.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorController Tests")
public class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    private static List<AuthorResponseDto> getAuthorsResponse;

    private static AuthorRequestDto authorRequestDto;

    private static AuthorResponseDto authorResponseDto;

    private static final String AUTHOR_REQUEST_JSON_PATH = "src/test/resources/AuthorRequest.json";

    private static final String AUTHOR_RESPONSE_JSON_PATH = "src/test/resources/AuthorResponse.json";

    private static final String GET_AUTHORS_RESPONSE_JSON_PATH = "src/test/resources/GetAuthorsResponse.json";

    public static final String EXPECTED_NAME = "Paúl";

    @BeforeAll
    public static void setUp() throws IOException {
        authorRequestDto = TestUtil.convertJsonToDto(AUTHOR_REQUEST_JSON_PATH, AuthorRequestDto.class);
        authorResponseDto = TestUtil.convertJsonToDto(AUTHOR_RESPONSE_JSON_PATH, AuthorResponseDto.class);
        getAuthorsResponse = TestUtil.convertJsonToDto(GET_AUTHORS_RESPONSE_JSON_PATH, new TypeReference<>() {
        });
    }

    @Test
    @DisplayName("Success Get Authors")
    void given_requestGetAuthors_when_getAuthorsCalled_then_returnsOkAndListOfAuthors() {
        when(this.authorService.getAllAuthors()).thenReturn(getAuthorsResponse);

        final var result = AuthorControllerTest.this.authorController.getAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(Objects.requireNonNull(result.getBody()).getFirst().getId());
        verify(this.authorService).getAllAuthors();
    }

    @Test
    @DisplayName("Create Author Success")
    void given_validAuthorRequest_when_createAuthorCalled_then_returnsCreatedAndAuthor() {
        when(this.authorService.createAuthor(authorRequestDto)).thenReturn(authorResponseDto);

        final var result = AuthorControllerTest.this.authorController.createAuthor(authorRequestDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(Objects.requireNonNull(result.getBody()).getId());
        assertEquals(EXPECTED_NAME, result.getBody().getName());
        verify(this.authorService).createAuthor(any(AuthorRequestDto.class));
    }
}
