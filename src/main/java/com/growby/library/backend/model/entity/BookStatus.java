package com.growby.library.backend.model.entity;

import com.growby.library.backend.exception.BookStatusException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.growby.library.backend.common.ValidationConstants.BOOK_STATUS_FIELD;
import static com.growby.library.backend.common.ValidationConstants.INVALID_BOOK_STATUS_MESSAGE;

@AllArgsConstructor
@Getter
public enum BookStatus {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not available");

    private static final Map<String, BookStatus> statusMap = new HashMap<>();

    static {
        for (BookStatus status : BookStatus.values()) {
            statusMap.put(status.value, status);
        }
    }

    private final String value;

    public static BookStatus fromValue(String value) {
        BookStatus status = statusMap.get(value);
        if (status != null) {
            return status;
        }
        throw new BookStatusException(HttpStatus.BAD_REQUEST,
                BOOK_STATUS_FIELD, value,
                INVALID_BOOK_STATUS_MESSAGE);
    }
}

