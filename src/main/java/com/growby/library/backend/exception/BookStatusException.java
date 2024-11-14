package com.growby.library.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.io.Serial;

@Getter
public class BookStatusException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 39348712723127893L;

    private final HttpStatusCode statusCode;

    private final String fieldName;

    private final String rejectedValue;

    public BookStatusException(final HttpStatusCode statusCode, final String fieldName, final String rejectedValue, final String message) {
        super(message);
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.statusCode = statusCode;
    }

}