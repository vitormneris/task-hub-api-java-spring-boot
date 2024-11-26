package com.codecrafters.taskhubcore.controller.usuarios.mapper;

import com.codecrafters.taskhubcore.controller.jobs.dto.JobDTO;
import com.codecrafters.taskhubcore.controller.usuarios.dto.UserDTO;
import com.codecrafters.taskhubcore.model.entities.JobEntity;
import com.codecrafters.taskhubcore.model.entities.UserEntity;
import com.codecrafters.taskhubcore.model.repositories.JobRepository;
import com.codecrafters.taskhubcore.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserWebMapper {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .name(userDTO.name())
                .phone(userDTO.phone())
                .email(userDTO.email())
                .password(userDTO.password())
                .build();
    }

    public UserDTO toDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .jobsCreated(userEntity.getJobsIdCreated() == null ? new HashSet<>() : userEntity.getJobsIdCreated().stream()
                        .map(id -> {
                            JobEntity jobEntity = jobRepository.findById(id).orElseThrow();
                            UserEntity crafter = userRepository.findById(jobEntity.getCrafterId()).orElseThrow();

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
                                            .id(crafter.getId())
                                            .name(crafter.getName())
                                            .phone(crafter.getPhone())
                                            .email(crafter.getEmail())
                                            .build())

                                    .subscribers(jobEntity.getSubscribersId().stream()
                                            .map(idUser -> {
                                                UserEntity user = userRepository.findById(idUser).orElseThrow();
                                                return UserDTO.builder()
                                                        .id(user.getId())
                                                        .name(user.getName())
                                                        .phone(user.getPhone())
                                                        .email(user.getEmail())
                                                        .build();
                                            }).collect(Collectors.toSet()))
                                    .build();
                        })
                        .collect(Collectors.toSet()))

                .jobsSubscribed(userEntity.getJobsIdSubscribed() == null ? new HashSet<>() : userEntity.getJobsIdSubscribed().stream()
                        .map(id -> {
                            JobEntity jobEntity = jobRepository.findById(id).orElseThrow();

                            UserEntity crafter = userRepository.findById(jobEntity.getCrafterId()).orElseThrow();

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
                                            .id(crafter.getId())
                                            .name(crafter.getName())
                                            .phone(crafter.getPhone())
                                            .email(crafter.getEmail())
                                            .build())
                                    .build();
                        })
                        .collect(Collectors.toSet()))
                .build();
    }
}
