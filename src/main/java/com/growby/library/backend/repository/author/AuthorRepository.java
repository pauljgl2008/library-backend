package com.growby.library.backend.repository.author;

import com.growby.library.backend.model.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    boolean isAuthorExists(Long id);

    List<Author> findAll();

    Optional<Author> findById(final Long id);

    boolean existsByName(final String name);

    Author save(final Author book);

    void deleteById(final Long id);
}
