package com.growby.library.backend.repository.loan;

import com.growby.library.backend.model.entity.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanJpaRepository extends CrudRepository<Loan, Long> {
    boolean existsByIdAndStatus(Long id, String status);
}
