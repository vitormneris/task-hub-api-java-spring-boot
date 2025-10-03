package com.codecrafters.taskhubcore.exceptions;

import com.codecrafters.taskhubcore.enums.RuntimeErrorEnum;
import lombok.Getter;

@Getter
public class DataIntegrationViolatedException extends RuntimeException {
    private final RuntimeErrorEnum error;

    public DataIntegrationViolatedException(RuntimeErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }
}
