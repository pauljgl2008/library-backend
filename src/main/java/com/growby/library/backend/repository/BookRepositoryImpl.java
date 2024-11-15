package com.growby.library.backend.repository;

import com.growby.library.backend.model.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private final BookCriteriaBuilder bookCriteriaBuilder;

    @Override
    public boolean isBookAvailable(Long id) {
        Optional<Book> book = bookJpaRepository.findAvailableBookById(id, "Available");
        return book.isPresent();
    }

    @Override
    public Page<Book> findAllWithPagination(Pageable pageable, String title, String author) {
        // Get the CriteriaBuilder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        // Build the Criteria query
        CriteriaQuery<Book> criteriaQuery = bookCriteriaBuilder.buildCriteriaQuery(criteriaBuilder, pageable, title, author);
        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
        // Apply pagination
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        // Execute the query
        List<Book> books = query.getResultList();
        // Count the total number of records with filtering
        CriteriaQuery<Long> countQuery = bookCriteriaBuilder.buildCountQuery(criteriaBuilder, title, author);
        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countQuery);
        Long count = countTypedQuery.getSingleResult();

        return new PageImpl<>(books, pageable, count);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookJpaRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        return this.bookJpaRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        this.bookJpaRepository.deleteById(id);
    }
}
