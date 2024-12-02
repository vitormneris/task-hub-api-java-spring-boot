package com.codecrafters.taskhubcore.service;

import com.codecrafters.taskhubcore.controller.usuarios.dto.UserDTO;
import com.codecrafters.taskhubcore.controller.usuarios.mapper.UserWebMapper;
import com.codecrafters.taskhubcore.model.entities.JobEntity;
import com.codecrafters.taskhubcore.model.entities.UserEntity;
import com.codecrafters.taskhubcore.model.repositories.JobRepository;
import com.codecrafters.taskhubcore.model.repositories.UserRepository;
import com.codecrafters.taskhubcore.utils.enums.RuntimeErrorEnum;
import com.codecrafters.taskhubcore.configuration.advice.exceptions.DataIntegrationViolatedException;
import com.codecrafters.taskhubcore.configuration.advice.exceptions.NotAuthorizedException;
import com.codecrafters.taskhubcore.configuration.advice.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final UserWebMapper mapper;

    public UserDTO findById(String id) {
        return mapper.toDTO(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0001)));
    }

    public UserDTO findByEmail(String email) {
        return mapper.toDTO(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0001)));
    }

    public UserDTO subscribeJob(String userId, String jobId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0001));
        JobEntity job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0002));

        if (job.getSubscribers().contains(user)) throw new DataIntegrationViolatedException(RuntimeErrorEnum.ERR0005);

        user.getJobsSubscribed().add(job);
        return mapper.toDTO(userRepository.save(user));
    }

    public UserDTO unsubscribeJob(String userId, String jobId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0001));
        JobEntity job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0002));

        if (!job.getSubscribers().contains(user)) throw new DataIntegrationViolatedException(RuntimeErrorEnum.ERR0006);

        user.getJobsSubscribed().remove(job);
        return mapper.toDTO(userRepository.save(user));
    }

    public Boolean login(UserDTO userDTO) {
        UserEntity entity = userRepository.findByEmail(userDTO.email()).orElseThrow(() -> new NotAuthorizedException(RuntimeErrorEnum.ERR0003));
        if (!entity.getPassword().equals(userDTO.password())) throw new NotAuthorizedException(RuntimeErrorEnum.ERR0003);
        return true;
    }

    public UserDTO save(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new DataIntegrationViolatedException(RuntimeErrorEnum.ERR0004);
        }
        return mapper.toDTO(userRepository.save(mapper.toEntity(userDTO)));
    }

    public UserDTO update(UserDTO userNew, String id) {
        UserEntity userOld = userRepository.findById(id).orElseThrow();

        if (userNew.email() != null && !userNew.email().equals(userOld.getEmail())) {
            if (userRepository.findByEmail(userNew.email()).isPresent()) {
                throw new DataIntegrationViolatedException(RuntimeErrorEnum.ERR0004);
            }
        }

        userOld.setName(userNew.name() == null ? userOld.getName() : userNew.name());
        userOld.setEmail(userNew.email() == null ? userOld.getEmail() : userNew.email());
        userOld.setPassword(userNew.password() == null ? userOld.getPassword() : userNew.password());
        userOld.setImageUrl(userNew.imageUrl() == null ? userOld.getImageUrl() : userNew.imageUrl());
        userOld.setPhone(userNew.phone() == null ? userOld.getPhone() : userNew.phone());
        return mapper.toDTO(userRepository.save(userOld));
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
