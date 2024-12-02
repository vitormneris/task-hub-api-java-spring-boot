package com.codecrafters.taskhubcore.configuration.advice;

import com.codecrafters.taskhubcore.configuration.advice.dto.ErrorMessageDTO;
import com.codecrafters.taskhubcore.configuration.advice.exceptions.DataIntegrationViolatedException;
import com.codecrafters.taskhubcore.configuration.advice.exceptions.NotAuthorizedException;
import com.codecrafters.taskhubcore.configuration.advice.exceptions.ResourceNotFoundException;
import com.codecrafters.taskhubcore.utils.enums.RuntimeErrorEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        RuntimeErrorEnum errorEnum = exception.getError();

        ErrorMessageDTO errorMessageResponseDTO = ErrorMessageDTO.builder()
                .code(errorEnum.getCode())
                .message(errorEnum.getMessage())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageResponseDTO);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorMessageDTO> notAuthorized(NotAuthorizedException exception, HttpServletRequest request) {
        RuntimeErrorEnum errorEnum = exception.getError();

        ErrorMessageDTO errorMessageResponseDTO = ErrorMessageDTO.builder()
                .code(errorEnum.getCode())
                .message(errorEnum.getMessage())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageResponseDTO);
    }

    @ExceptionHandler(DataIntegrationViolatedException.class)
    public ResponseEntity<ErrorMessageDTO> dataIntegrationViolated(DataIntegrationViolatedException exception, HttpServletRequest request) {
        RuntimeErrorEnum errorEnum = exception.getError();

        ErrorMessageDTO errorMessageResponseDTO = ErrorMessageDTO.builder()
                .code(errorEnum.getCode())
                .message(errorEnum.getMessage())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessageResponseDTO);
    }
}
