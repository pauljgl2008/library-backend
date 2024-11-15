package com.growby.library.backend.model.dto.loan;

import com.growby.library.backend.model.dto.book.BookResponseDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanResponseDto {

    private Long id;

    private LocalDate loanDate;

    private LocalDate returnDate;

    private String status;

    private BookResponseDto book;
}
