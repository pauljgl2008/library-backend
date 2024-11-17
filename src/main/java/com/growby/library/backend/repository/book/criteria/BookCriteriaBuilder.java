package com.growby.library.backend.repository.book.criteria;

import com.growby.library.backend.common.ValidationConstants;
import com.growby.library.backend.exception.InvalidFieldException;
import com.growby.library.backend.model.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class BookCriteriaBuilder {

    private static final String PERCENTAGE_SYMBOL = "%";
    private static final String ATTRIBUTE_PUBLICATION_DATE = "publicationDate";
    private static final String KEY_PUBLICATION_DATE = "publication_date";
    private static final Set<String> VALID_SORT_FIELDS = Set.of(
            "id", "author", "isbn", "status", ATTRIBUTE_PUBLICATION_DATE
    );

    public CriteriaQuery<Book> buildCriteriaQuery(
            CriteriaBuilder criteriaBuilder,
            Pageable pageable,
            String title,
            String author) {

        var criteriaQuery = criteriaBuilder.createQuery(Book.class);
        var root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);

        var predicates = this.buildPredicates(criteriaBuilder, root, title, author);
        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        this.addSorting(criteriaBuilder, pageable, root, criteriaQuery);

        return criteriaQuery;
    }

    public CriteriaQuery<Long> buildCountQuery(
            CriteriaBuilder criteriaBuilder,
            String title,
            String author) {

        var countQuery = criteriaBuilder.createQuery(Long.class);
        var countRoot = countQuery.from(Book.class);
        countQuery.select(criteriaBuilder.count(countRoot));

        var predicates = this.buildPredicates(criteriaBuilder, countRoot, title, author);
        if (!predicates.isEmpty()) {
            countQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        return countQuery;
    }

    private List<Predicate> buildPredicates(CriteriaBuilder criteriaBuilder, Root<Book> root, String title, String author) {
        return Stream.of(title, author)
                .filter(StringUtils::isNotEmpty)
                .map(value -> criteriaBuilder.like(criteriaBuilder.lower(root.get(value)), PERCENTAGE_SYMBOL + value.toLowerCase() + PERCENTAGE_SYMBOL))
                .toList();
    }

    private void addSorting(CriteriaBuilder criteriaBuilder, Pageable pageable, Root<Book> root, CriteriaQuery<?> criteriaQuery) {
        pageable.getSort().forEach(order -> {
            String property = order.getProperty().equals(KEY_PUBLICATION_DATE)
                    ? ATTRIBUTE_PUBLICATION_DATE
                    : order.getProperty();

            if (!VALID_SORT_FIELDS.contains(property)) {
                throw new InvalidFieldException(HttpStatus.BAD_REQUEST,
                        property,
                        property,
                        ValidationConstants.ORDER_EXCEPTION_MESSAGE);
            }

            criteriaQuery.orderBy(order.getDirection().isAscending()
                    ? criteriaBuilder.asc(root.get(property))
                    : criteriaBuilder.desc(root.get(property)));
        });
    }
}
