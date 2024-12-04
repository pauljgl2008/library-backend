package com.growby.library.backend.model.dto.author;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorResponseDto {

    private Long id;

    private String name;

    private String nationality;

    @JsonProperty("birth_date")
    private String birthDate;
}

