package com.growby.library.backend.model.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growby.library.backend.model.dto.author.AuthorResponseDto;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import static com.growby.library.backend.common.ValidationConstants.PUBLICATION_DATE_PARAM;

@Data
public class BookResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private AuthorResponseDto author;

    private String isbn;

    @JsonProperty(PUBLICATION_DATE_PARAM)
    private LocalDate publicationDate;

    private String status;
}
