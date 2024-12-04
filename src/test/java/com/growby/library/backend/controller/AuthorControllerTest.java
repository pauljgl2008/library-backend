package com.growby.library.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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

    private static final String GET_AUTHORS_RESPONSE_JSON_PATH =  "src/test/resources/GetAuthorsResponse.json";

    @BeforeAll
    public static void setUp() throws IOException {
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

}
