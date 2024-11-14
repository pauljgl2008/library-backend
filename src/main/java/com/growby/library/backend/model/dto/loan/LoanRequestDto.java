package com.growby.library.backend.model.dto.loan;

import com.growby.library.backend.model.entity.Book;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanRequestDto {
    private Long id;

    private LocalDate loanDate;
    private LocalDate returnDate;
    private String status; // Active / Completed

    @ManyToOne
    private Book book;
}
