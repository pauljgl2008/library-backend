package com.growby.library.backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name="BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

    private String isbn;

    private LocalDate publicationDate;

    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<Loan> loans;
}
