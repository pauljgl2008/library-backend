package com.growby.library.backend.exception;

import com.growby.library.backend.model.dto.error.ErrorDto;
import com.growby.library.backend.model.dto.error.ErrorResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static com.growby.library.backend.common.ValidationConstants.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  @NonNull final HttpHeaders headers,
                                                                  @NonNull final HttpStatusCode status,
                                                                  @NonNull final WebRequest request) {

        return this.retrieveBadRequest(ex.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                        WebRequest request) {
        return new ResponseEntity<>(DATA_INTEGRITY_VIOLATION_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public final ResponseEntity<Object> handleBookNotFoundException(final BookNotFoundException ex) {
        return this.retrieveBadRequest(
                List.of(this.buildFieldError(ex.getFieldName(), ex.getRejectedValue(), ex.getMessage())));
    }

    @ExceptionHandler(InvalidFieldException.class)
    public final ResponseEntity<Object> serviceExceptionHandler(final InvalidFieldException ex) {
        return this.retrieveBadRequest(
                List.of(this.buildFieldError(ex.getFieldName(), ex.getRejectedValue(), ex.getMessage())));
    }

    private ResponseEntity<Object> retrieveBadRequest(List<FieldError> fieldErrors) {
        List<ErrorDto> errors = fieldErrors.stream()
                .map(err -> new ErrorDto(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .toList();

        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                VALIDATION_FIELD,
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }


    private FieldError buildFieldError(final String fieldName,
                                       final String rejectedValue,
                                       final String message) {
        return new FieldError(StringUtils.EMPTY, fieldName, rejectedValue, true,
                null, null, message);
    }
}
