package com.growby.library.backend.model.dto.author;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorResponseDto {
    private Long id;
    private String name;
    private String nationality;
    private LocalDate birthDate;
}

