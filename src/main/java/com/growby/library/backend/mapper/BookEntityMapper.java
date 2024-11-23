package com.growby.library.backend.mapper;

import com.growby.library.backend.model.dto.book.BookRequestDto;
import com.growby.library.backend.model.dto.book.BookResponseDto;
import com.growby.library.backend.model.entity.Book;
import com.growby.library.backend.model.entity.BookStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookEntityMapper {

    BookEntityMapper INSTANCE = Mappers.getMapper(BookEntityMapper.class);

    List<BookResponseDto> bookListToBookResponseDtoList(List<Book> source);

    @Mapping(source = "author.id", target = "authorId")
    BookResponseDto toBookResponseDto(Book source);

    @Mapping(source = "authorId", target = "author.id")
    Book fromBookRequestDto(BookRequestDto source);

    default BookStatus toBookStatusEnum(String status) {
        return BookStatus.fromValue(status);
    }
}
