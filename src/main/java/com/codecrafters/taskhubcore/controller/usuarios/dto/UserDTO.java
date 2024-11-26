package com.codecrafters.taskhubcore.controller.usuarios.dto;

import com.codecrafters.taskhubcore.controller.jobs.dto.JobDTO;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserDTO(
        String id,
        String name,
        String email,
        String phone,
        String password,
        Set<JobDTO> jobsCreated,
        Set<JobDTO> jobsSubscribed) {
}
