package com.growby.library.backend.repository;

import com.growby.library.backend.model.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJpaRepository extends CrudRepository<Book, Long> {
    boolean existsByIdAndStatus(Long id, String status);

    boolean existsByIsbn(String isbn);
}
