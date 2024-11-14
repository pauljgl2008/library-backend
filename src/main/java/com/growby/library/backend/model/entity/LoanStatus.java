package com.growby.library.backend.model.entity;

import lombok.Getter;

@Getter
public enum LoanStatus {
    ACTIVE("Active"),
    COMPLETED("Completed");

    private final String status;

    LoanStatus(String status) {
        this.status = status;
    }

}

