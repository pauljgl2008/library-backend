package com.growby.library.backend.repository.impl;

import com.growby.library.backend.model.entity.Loan;
import com.growby.library.backend.repository.LoanJpaRepository;
import com.growby.library.backend.repository.LoanRepository;
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
        return loanJpaRepository.existsByIdAndCompleted(id, status);
    }
}
