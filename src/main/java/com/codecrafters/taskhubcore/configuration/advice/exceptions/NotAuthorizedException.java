package com.codecrafters.taskhubcore.configuration.advice.exceptions;

import com.codecrafters.taskhubcore.utils.enums.RuntimeErrorEnum;
import lombok.Getter;

@Getter
public class NotAuthorizedException extends RuntimeException {
    private final RuntimeErrorEnum error;

    public NotAuthorizedException(RuntimeErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }
}
