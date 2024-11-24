package com.growby.library.backend.repository.book;

import com.growby.library.backend.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    boolean isBookAvailable(Long bookId);

    Page<Book> findAllWithPagination(Pageable pageable, String title, String author);

    boolean existsByIsbn(final String isbn);

    Optional<Book> findById(final Long id);

    Book save(final Book book);

    void deleteById(final Long id);
}
