package com.growby.library.backend.service;

import com.growby.library.backend.model.dto.loan.LoanRequestDto;
import com.growby.library.backend.model.dto.loan.LoanResponseDto;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    Optional<LoanResponseDto> getLoanById(Long bookId);

    List<LoanResponseDto> getLoans();

    LoanResponseDto createLoan(LoanRequestDto bookRequestDto);

    LoanResponseDto updateLoan(Long id, LoanRequestDto bookRequestDto);

    void deleteLoanById(Long id);
}

