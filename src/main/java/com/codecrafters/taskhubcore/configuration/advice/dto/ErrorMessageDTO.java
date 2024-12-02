package com.codecrafters.taskhubcore.configuration.advice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorMessageDTO(
        String code,
        String message,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
        Instant timestamp,
        String path
) {
}
