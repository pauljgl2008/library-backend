package com.growby.library.backend.repository.book.impl;

import com.growby.library.backend.model.entity.Book;
import com.growby.library.backend.model.entity.BookStatus;
import com.growby.library.backend.repository.book.criteria.BookCriteriaBuilder;
import com.growby.library.backend.repository.book.BookJpaRepository;
import com.growby.library.backend.repository.book.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private final BookCriteriaBuilder bookCriteriaBuilder;

    @Override
    public boolean isBookAvailable(Long id) {
        return this.bookJpaRepository.existsByIdAndStatus(id, BookStatus.AVAILABLE.getValue());
    }

    @Override
    public Page<Book> findAllWithPagination(Pageable pageable, String title, String author) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Book> criteriaQuery = this.bookCriteriaBuilder.buildCriteriaQuery(
                criteriaBuilder, pageable, title, author);
        TypedQuery<Book> query = this.entityManager.createQuery(criteriaQuery);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<Book> books = query.getResultList();

        CriteriaQuery<Long> countQuery = this.bookCriteriaBuilder.buildCountQuery(criteriaBuilder, title, author);
        TypedQuery<Long> countTypedQuery = this.entityManager.createQuery(countQuery);
        Long count = countTypedQuery.getSingleResult();

        return new PageImpl<>(books, pageable, count);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookJpaRepository.findById(id);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return this.bookJpaRepository.existsByIsbn(isbn);
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