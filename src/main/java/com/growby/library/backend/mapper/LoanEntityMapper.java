package com.growby.library.backend.mapper;

import com.growby.library.backend.model.dto.loan.LoanRequestDto;
import com.growby.library.backend.model.dto.loan.LoanResponseDto;
import com.growby.library.backend.model.entity.Author;
import com.growby.library.backend.model.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanEntityMapper {

    LoanEntityMapper INSTANCE = Mappers.getMapper(LoanEntityMapper.class);

    @Mapping(source = "book.id", target = "bookId")
    LoanResponseDto toLoanResponseDto(Loan source);

    @Mapping(source = "bookId", target = "book.id")
    Loan fromLoanRequestDto(LoanRequestDto source);

    List<LoanResponseDto> loanListToLoanResponseDtoList(List<Loan> source);

    default String map(Author author) {
        return author != null ? author.getName() : null;
    }
}
