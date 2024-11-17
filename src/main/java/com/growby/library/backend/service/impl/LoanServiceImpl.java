package com.growby.library.backend.service.impl;

import com.growby.library.backend.common.ValidationConstants;
import com.growby.library.backend.exception.BookNotFoundException;
import com.growby.library.backend.mapper.LoanEntityMapperImpl;
import com.growby.library.backend.model.dto.loan.LoanRequestDto;
import com.growby.library.backend.model.dto.loan.LoanResponseDto;
import com.growby.library.backend.model.entity.Loan;
import com.growby.library.backend.model.entity.LoanStatus;
import com.growby.library.backend.repository.BookJpaRepository;
import com.growby.library.backend.repository.impl.LoanRepositoryImpl;
import com.growby.library.backend.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.growby.library.backend.common.ValidationConstants.PARAM_ID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanEntityMapperImpl loanEntityMapper;
    private final LoanRepositoryImpl loanRepository;
    private final BookJpaRepository bookJpaRepository;

    @Override
    public boolean isLoanExistsAndCompleted(Long id) {
        return this.loanRepository.isLoanExistsAndCompleted(id, LoanStatus.COMPLETED.getValue());
    }

    @Override
    public List<LoanResponseDto> getAllLoans() {
        List<Loan> loans = this.loanRepository.findAll();
        return this.loanEntityMapper.loanListToLoanResponseDtoList(loans);
    }

    @Override
    public Optional<LoanResponseDto> getLoanById(Long id) {
        Optional<Loan> loan = this.loanRepository.findById(id);
        return loan.map(this.loanEntityMapper::toLoanResponseDto);
    }

    @Override
    public LoanResponseDto createLoan(LoanRequestDto loanRequestDto) {
        Loan loan = this.loanEntityMapper.fromLoanRequestDto(loanRequestDto);
        loan = this.loanRepository.save(loan);
        return this.loanEntityMapper.toLoanResponseDto(loan);
    }

    @Override
    public LoanResponseDto updateLoan(Long id, LoanRequestDto loanRequestDto) {
        Loan loan = this.loanRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(HttpStatus.BAD_REQUEST, PARAM_ID, id.toString(),
                        ValidationConstants.AUTHOR_NOT_FOUND_MESSAGE));
        loan.setId(loanRequestDto.getId());
        loan.setLoanDate(loanRequestDto.getLoanDate());
        loan.setStatus(loanRequestDto.getStatus());
        loan.setBook(this.bookJpaRepository.findById(loanRequestDto.getId())
                .orElseThrow(() -> new BookNotFoundException(HttpStatus.BAD_REQUEST, PARAM_ID, id.toString(),
                        ValidationConstants.LOAN_NOT_FOUND_MESSAGE)));
        loan = this.loanRepository.save(loan);
        return this.loanEntityMapper.toLoanResponseDto(loan);
    }

    @Override
    public void deleteLoanById(Long id) {
        this.loanRepository.deleteById(id);
    }
}
