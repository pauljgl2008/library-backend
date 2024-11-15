package com.growby.library.backend.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {
    public static final String BOOK_ISBN_REQUIRED_MESSAGE = "El campo isbn es requerido";
    public static final String BOOK_STATUS_REQUIRED_MESSAGE = "El campo status es requerido";
    public static final String BOOK_PUBLICATION_DATE_REQUIRED_MESSAGE = "El campo publication_date es requerido";

    public static final String PUBLICATION_DATE_PARAM = "publication_date";
    public static final String BOOK_STATUS_PARAM = "status";

    public static final String BOOK_STATUS_REGEX_PATTERN = "(Disponible|No disponible)";
    public static final String BOOK_STATUS_INVALID_FORMAT_MESSAGE = "El campo estado solo puede tener valor de 'Disponible' o 'No disponible'";

    public static final String VALIDATION_FIELD = "Validation failed";

    public static final String BOOK_NOT_FOUND_MESSAGE = "El libro no fue encontrado con el id proporcionado.";
    public static final String BOOK_ISBN_EXISTS_MESSAGE = "El isbn del libro ya existe.";

    public static final String DATA_INTEGRITY_VIOLATION_EXCEPTION_MESSAGE = "Error de integridad en los datos. Verifica los valores enviados.";
    public static final String GENERIC_EXCEPTION_MESSAGE = "Se ha producido un error inesperado en el servidor.";

    public static final String LOAN_STATUS_FIELD = "estado del préstamo";
    public static final String INVALID_LOAN_STATUS_MESSAGE = "El estado del préstamo solo puede ser 'Activo' o 'Finalizado'.";

    public static final String BOOK_STATUS_FIELD = "estado del libro";
    public static final String INVALID_BOOK_STATUS_MESSAGE = "El estado del libro solo puede ser 'Disponible' o 'No disponible'.";

    public static final String BOOK_STATUS_AVAILABLE_MESSAGE = "El libro está disponible.";
    public static final String BOOK_STATUS_NOT_AVAILABLE_MESSAGE = "El libro no está disponible.";
}
