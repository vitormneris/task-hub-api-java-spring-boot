package com.codecrafters.taskhubcore.controller.job.mapper;

import com.codecrafters.taskhubcore.controller.job.dto.JobDTO;
import com.codecrafters.taskhubcore.controller.user.dto.UserDTO;
import com.codecrafters.taskhubcore.model.JobEntity;
import com.codecrafters.taskhubcore.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobWebMapper {

    public JobEntity toEntity(JobDTO jobDTO) {
        return JobEntity.builder()
                .title(jobDTO.title())
                .moment(jobDTO.moment())
                .details(jobDTO.details())
                .imageUrl(jobDTO.imageUrl())
                .payment(jobDTO.payment())
                .available(jobDTO.available())
                .address(jobDTO.address())
                .crafter(UserEntity.builder()
                        .id(jobDTO.crafter().id())
                        .name(jobDTO.crafter().name())
                        .imageUrl(jobDTO.crafter().imageUrl())
                        .phone(jobDTO.crafter().phone())
                        .email(jobDTO.crafter().password())
                        .build())
                .build();
    }

    public List<JobDTO> toListDTO(List<JobEntity> jobEntities) {
        List<JobDTO> jobsDTO = new ArrayList<>();
        jobEntities.forEach(job -> jobsDTO.add(toDTO(job)));
        return jobsDTO;
    }


    public JobDTO toDTO(JobEntity jobEntity) {
        return JobDTO.builder()
                .id(jobEntity.getId())
                .title(jobEntity.getTitle())
                .moment(jobEntity.getMoment())
                .details(jobEntity.getDetails())
                .imageUrl(jobEntity.getImageUrl())
                .payment(jobEntity.getPayment())
                .available(jobEntity.getAvailable())
                .address(jobEntity.getAddress())

                .crafter(UserDTO.builder()
                        .id(jobEntity.getCrafter().getId())
                        .name(jobEntity.getCrafter().getName())
                        .imageUrl(jobEntity.getCrafter().getImageUrl())
                        .phone(jobEntity.getCrafter().getPhone())
                        .email(jobEntity.getCrafter().getEmail())
                        .build())

                .subscribers(jobEntity.getSubscribers() == null ? new HashSet<>() : jobEntity.getSubscribers().stream()
                        .map(user -> UserDTO.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .imageUrl(user.getImageUrl())
                                .phone(user.getPhone())
                                .email(user.getEmail())
                                .build()).collect(Collectors.toSet()))
                .build();
    }
}
