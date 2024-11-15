package com.growby.library.backend.model.entity;

import com.growby.library.backend.exception.LoanStatusException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.growby.library.backend.common.ValidationConstants.*;

@AllArgsConstructor
@Getter
public enum LoanStatus {
    ACTIVE("Activo"),
    COMPLETED("Finalizado");

    private static final Map<String, LoanStatus> statusMap = new HashMap<>();

    static {
        for (LoanStatus status : LoanStatus.values()) {
            statusMap.put(status.value, status);
        }
    }

    private final String value;

    public static LoanStatus fromValue(String value) {
        LoanStatus status = statusMap.get(value);
        if (status != null) {
            return status;
        }
        throw new LoanStatusException(HttpStatus.BAD_REQUEST,
                LOAN_STATUS_FIELD, value,
                INVALID_LOAN_STATUS_MESSAGE);
    }
}

