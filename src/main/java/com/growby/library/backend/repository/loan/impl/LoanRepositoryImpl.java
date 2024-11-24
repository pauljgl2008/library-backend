package com.growby.library.backend.repository.loan.impl;

import com.growby.library.backend.model.dto.loan.LoanResponseDto;
import com.growby.library.backend.model.entity.Loan;
import com.growby.library.backend.repository.loan.LoanJpaRepository;
import com.growby.library.backend.repository.loan.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LoanRepositoryImpl implements LoanRepository {
    private final LoanJpaRepository loanJpaRepository;

    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanJpaRepository.findAll();
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return this.loanJpaRepository.findById(id);
    }

    @Override
    public Loan save(Loan author) {
        return this.loanJpaRepository.save(author);
    }

    @Override
    public void deleteById(Long id) {
        this.loanJpaRepository.deleteById(id);
    }

    @Override
    public boolean isLoanExistsAndCompleted(Long id, String status) {
        return this.loanJpaRepository.existsByIdAndStatus(id, status);
    }

    @Override
    public List<Loan> findByBookId(Long bookId) {
        return this.loanJpaRepository.findByBookId(bookId);
    }
}
