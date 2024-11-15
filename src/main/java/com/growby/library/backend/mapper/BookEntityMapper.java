package com.growby.library.backend.mapper;

import com.growby.library.backend.model.dto.book.BookRequestDto;
import com.growby.library.backend.model.dto.book.BookResponseDto;
import com.growby.library.backend.model.entity.Author;
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
    @Mapping(source = "author.name", target = "author") // Map Long author.id to Author object
    BookResponseDto toBookResponseDto(Book source);

    @Mapping(source = "author", target = "author.id") // Map Author object to Long author.id
    Book fromBookRequestDto(BookRequestDto source);

    default BookStatus toBookStatusEnum(String status) {
        return BookStatus.fromValue(status);
    }
}
