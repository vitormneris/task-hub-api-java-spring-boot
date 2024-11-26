package com.codecrafters.taskhubcore.controller.jobs.dto;

import lombok.Builder;

@Builder
public record AddressDTO(
        String state,
        String city,
        String neighborhood,
        String complement
) {
}
