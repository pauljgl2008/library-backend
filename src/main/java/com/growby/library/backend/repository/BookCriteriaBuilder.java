package com.growby.library.backend.repository;

import com.growby.library.backend.model.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookCriteriaBuilder {

    /**
     * Builds the CriteriaQuery for fetching the books with pagination and optional filtering.
     *
     * @param criteriaBuilder the CriteriaBuilder
     * @param pageable the Pageable object containing pagination and sorting information
     * @param title filter by book title (optional)
     * @param author filter by book author (optional)
     * @return the CriteriaQuery for fetching books
     */
    public CriteriaQuery<Book> buildCriteriaQuery(
            CriteriaBuilder criteriaBuilder,
            Pageable pageable,
            String title,
            String author) {

        // Create the Criteria query
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);

        // Add dynamic filters (e.g., by title and author)
        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (author != null && !author.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
        }

        // Combine all predicates into one 'where' clause
        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        // Add sorting if present in the Pageable object
        pageable.getSort();
        for (Sort.Order order : pageable.getSort()) {
            String property = order.getProperty();
            boolean ascending = order.getDirection().isAscending();
            criteriaQuery.orderBy(ascending
                    ? criteriaBuilder.asc(root.get(property))
                    : criteriaBuilder.desc(root.get(property)));
        }

        return criteriaQuery;
    }

    /**
     * Builds the CriteriaQuery for counting the total number of books with optional filtering.
     *
     * @param criteriaBuilder the CriteriaBuilder
     * @param title filter by book title (optional)
     * @param author filter by book author (optional)
     * @return the CriteriaQuery for counting books
     */
    public CriteriaQuery<Long> buildCountQuery(
            CriteriaBuilder criteriaBuilder,
            String title,
            String author) {

        // Create the count query
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Book> countRoot = countQuery.from(Book.class);
        countQuery.select(criteriaBuilder.count(countRoot));

        // Add dynamic filters (e.g., by title and author)
        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(countRoot.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (author != null && !author.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(countRoot.get("author")), "%" + author.toLowerCase() + "%"));
        }

        // Combine all predicates into one 'where' clause
        if (!predicates.isEmpty()) {
            countQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        return countQuery;
    }
}
