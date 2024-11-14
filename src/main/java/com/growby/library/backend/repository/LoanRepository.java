package com.growby.library.backend.repository;

import com.growby.library.backend.model.entity.Book;
import com.growby.library.backend.model.entity.Loan;
import com.growby.library.backend.model.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByBook(Book book);

    long countByBookAndStatus(Book book, LoanStatus status);

}
