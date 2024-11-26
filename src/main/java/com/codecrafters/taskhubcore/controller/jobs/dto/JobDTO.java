package com.codecrafters.taskhubcore.controller.jobs.dto;

import com.codecrafters.taskhubcore.controller.usuarios.dto.UserDTO;
import lombok.Builder;

import java.util.Set;

@Builder
public record JobDTO(
        String id,
        String title,
        String moment,
        String details,
        String imageUrl,
        Double payment,
        Boolean available,
        String address,
        UserDTO crafter,
        Set<UserDTO> subscribers
) {
}
