package com.growby.library.backend.repository.loan;

import com.growby.library.backend.model.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    List<Loan> findAll();

    Optional<Loan> findById(final Long id);

    Loan save(final Loan book);

    void deleteById(final Long id);

    boolean isLoanExistsAndCompleted(Long id, String status);
}
