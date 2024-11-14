package com.growby.library.backend.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {
    public static final String BOOK_NOT_EXISTS_MESSAGE  = "El libro no fue encontrado con el id";
    public static final String BOOK_STATUS_FIELD  = "estado del libro";
    public static final String INVALID_BOOK_STATUS_MESSAGE = "El estado del libro solo puede ser 'activo' o 'inactivo'.";
}
