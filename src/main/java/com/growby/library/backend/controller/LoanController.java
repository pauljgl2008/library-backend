package com.growby.library.backend.controller;

import com.growby.library.backend.model.dto.loan.LoanRequestDto;
import com.growby.library.backend.model.dto.loan.LoanResponseDto;
import com.growby.library.backend.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.growby.library.backend.common.ValidationConstants.BOOK_STATUS_AVAILABLE_MESSAGE;
import static com.growby.library.backend.common.ValidationConstants.BOOK_STATUS_NOT_AVAILABLE_MESSAGE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        List<LoanResponseDto> loans = this.loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @PostMapping
    public ResponseEntity<LoanResponseDto> createAuthor(@Valid @RequestBody LoanRequestDto loanRequestDto) {
        LoanResponseDto authorCreated = this.loanService.createLoan(loanRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorCreated);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<String> checkLoanAvailability(@PathVariable Long id) {
        boolean isAvailable = this.loanService.isLoanExistsAndCompleted(id);
        if (isAvailable) {
            return ResponseEntity.ok(BOOK_STATUS_AVAILABLE_MESSAGE);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BOOK_STATUS_NOT_AVAILABLE_MESSAGE);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Long id) {
        return this.loanService.getLoanById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDto> updateLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanRequestDto loanRequestDto) {
        LoanResponseDto updatedBook = this.loanService.updateLoan(id, loanRequestDto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        this.loanService.deleteLoanById(id);
        return ResponseEntity.noContent().build();
    }
}
