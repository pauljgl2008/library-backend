package com.growby.library.backend.model.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import static com.growby.library.backend.common.ValidationConstants.*;

@Data
public class BookRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String author;

    @NotBlank(message = BOOK_ISBN_REQUIRED_MESSAGE)
    private String isbn;

    @NotNull(message = BOOK_PUBLICATION_DATE_REQUIRED_MESSAGE)
    @JsonProperty(PUBLICATION_DATE_PARAM)
    private LocalDate publicationDate;

    @NotNull(message = BOOK_STATUS_REQUIRED_MESSAGE)
    @Pattern(regexp = BOOK_STATUS_REGEX_PATTERN, message = BOOK_STATUS_INVALID_FORMAT_MESSAGE)
    @JsonProperty(BOOK_STATUS_PARAM)
    private String status;
}
