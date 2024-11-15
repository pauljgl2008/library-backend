package com.growby.library.backend.repository;

import com.growby.library.backend.model.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookJpaRepository extends CrudRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.id = :id AND b.status = :status")
    Optional<Book> findAvailableBookById(Long id, String status);
}
