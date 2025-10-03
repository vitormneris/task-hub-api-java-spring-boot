package com.codecrafters.taskhubcore.exceptions;

import com.codecrafters.taskhubcore.enums.RuntimeErrorEnum;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final RuntimeErrorEnum error;

    public ResourceNotFoundException(RuntimeErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }
}
