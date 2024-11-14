package com.growby.library.backend.model.dto.loan;

import com.growby.library.backend.model.dto.book.BookResponseDto;
import com.growby.library.backend.model.entity.LoanStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LoanResponseDto {
    private Long id;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private String status; // Active / Completed
    private BookResponseDto book; // Cambiar a un solo BookResponseDto
}
