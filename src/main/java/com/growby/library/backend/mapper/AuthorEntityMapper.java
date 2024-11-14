package com.growby.library.backend.mapper;

import com.growby.library.backend.model.dto.author.AuthorRequestDto;
import com.growby.library.backend.model.dto.author.AuthorResponseDto;
import com.growby.library.backend.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorEntityMapper {

    AuthorEntityMapper INSTANCE = Mappers.getMapper(AuthorEntityMapper.class);

    AuthorResponseDto toAuthorResponseDto(Author source);

    Author fromAuthorRequestDto(AuthorRequestDto source);

    List<AuthorResponseDto> authorListToAuthorResponseDtoList(List<Author> source);
}
