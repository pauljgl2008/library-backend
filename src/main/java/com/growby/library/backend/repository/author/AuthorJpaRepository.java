package com.growby.library.backend.repository.author;

import com.growby.library.backend.model.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorJpaRepository extends CrudRepository<Author, Long> {
    boolean existsByName(String isbn);
}
