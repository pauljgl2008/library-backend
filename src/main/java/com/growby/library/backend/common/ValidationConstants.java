package com.growby.library.backend.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {
    public static final String BOOK_NOT_FOUND_MESSAGE = "El libro no fue encontrado con el id proporcionado.";

    public static final String LOAN_STATUS_FIELD = "estado del préstamo";
    public static final String INVALID_LOAN_STATUS_MESSAGE = "El estado del préstamo solo puede ser 'Activo' o 'Finalizado'.";

    public static final String BOOK_STATUS_FIELD = "estado del libro";
    public static final String INVALID_BOOK_STATUS_MESSAGE = "El estado del libro solo puede ser 'Disponible' o 'No disponible'.";

    public static final String BOOK_STATUS_AVAILABLE_MESSAGE = "El libro está disponible.";
    public static final String BOOK_STATUS_NOT_AVAILABLE_MESSAGE = "El libro no está disponible.";
}
