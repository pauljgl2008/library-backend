package com.growby.library.backend.model.dto.book;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequestDto {

    private String id;

    private String title;

    private String author;

    private String isbn;

    private LocalDate publicationDate;

    private String status;
}