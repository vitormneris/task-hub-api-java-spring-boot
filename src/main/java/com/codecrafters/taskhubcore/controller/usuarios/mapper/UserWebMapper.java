package com.codecrafters.taskhubcore.controller.usuarios.mapper;

import com.codecrafters.taskhubcore.controller.jobs.dto.JobDTO;
import com.codecrafters.taskhubcore.controller.usuarios.dto.UserDTO;
import com.codecrafters.taskhubcore.model.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class UserWebMapper {

    public UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .name(userDTO.name())
                .phone(userDTO.phone())
                .email(userDTO.email())
                .imageUrl(userDTO.imageUrl())
                .password(userDTO.password())
                .build();
    }

    public UserDTO toDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .imageUrl(userEntity.getImageUrl())
                .password(userEntity.getPassword())
                .jobsCreated(userEntity.getJobsCreated() == null ? new HashSet<>() : userEntity.getJobsCreated().stream()
                        .map(job -> JobDTO.builder()
                                .id(job.getId())
                                .title(job.getTitle())
                                .moment(job.getMoment())
                                .details(job.getDetails())
                                .imageUrl(job.getImageUrl())
                                .payment(job.getPayment())
                                .available(job.getAvailable())
                                .address(job.getAddress())
                                .crafter(UserDTO.builder()
                                        .id(job.getCrafter().getId())
                                        .name(job.getCrafter().getName())
                                        .imageUrl(job.getCrafter().getImageUrl())
                                        .phone(job.getCrafter().getPhone())
                                        .email(job.getCrafter().getEmail())
                                        .build())

                                .subscribers(job.getSubscribers().stream()
                                        .map(user -> UserDTO.builder()
                                                .id(user.getId())
                                                .name(user.getName())
                                                .phone(user.getPhone())
                                                .imageUrl(user.getImageUrl())
                                                .email(user.getEmail())
                                                .build()).collect(Collectors.toSet()))
                                .build())
                        .collect(Collectors.toSet()))

                .jobsSubscribed(userEntity.getJobsSubscribed() == null ? new HashSet<>() : userEntity.getJobsSubscribed().stream()
                        .map(jobUser -> JobDTO.builder()
                                .id(jobUser.getId())
                                .title(jobUser.getTitle())
                                .moment(jobUser.getMoment())
                                .details(jobUser.getDetails())
                                .imageUrl(jobUser.getImageUrl())
                                .payment(jobUser.getPayment())
                                .available(jobUser.getAvailable())
                                .address(jobUser.getAddress())
                                .crafter(UserDTO.builder()
                                        .id(jobUser.getCrafter().getId())
                                        .name(jobUser.getCrafter().getName())
                                        .phone(jobUser.getCrafter().getPhone())
                                        .imageUrl(jobUser.getCrafter().getImageUrl())
                                        .email(jobUser.getCrafter().getEmail())
                                        .build())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
