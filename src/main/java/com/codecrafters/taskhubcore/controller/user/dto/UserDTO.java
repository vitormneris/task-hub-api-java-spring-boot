package com.codecrafters.taskhubcore.controller.user.dto;

import com.codecrafters.taskhubcore.controller.job.dto.JobDTO;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserDTO(
        String id,
        String name,
        String email,
        String phone,
        String password,
        String imageUrl,
        Set<JobDTO> jobsCreated,
        Set<JobDTO> jobsSubscribed) {
}
