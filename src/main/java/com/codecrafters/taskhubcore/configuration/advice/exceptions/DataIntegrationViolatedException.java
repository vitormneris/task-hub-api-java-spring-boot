package com.codecrafters.taskhubcore.configuration.advice.exceptions;

import com.codecrafters.taskhubcore.utils.enums.RuntimeErrorEnum;
import lombok.Getter;

@Getter
public class DataIntegrationViolatedException extends RuntimeException {
    private final RuntimeErrorEnum error;

    public DataIntegrationViolatedException(RuntimeErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }
}
