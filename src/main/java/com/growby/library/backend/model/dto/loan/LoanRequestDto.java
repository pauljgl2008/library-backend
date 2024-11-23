package com.growby.library.backend.model.dto.loan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanRequestDto {

    private Long id;

    @JsonProperty("loan_date")
    private LocalDate loanDate;

    @JsonProperty("return_date")
    private LocalDate returnDate;

    private String status;

    @JsonProperty("book_id")
    private Long bookId;
}
