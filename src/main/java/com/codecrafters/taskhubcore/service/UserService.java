package com.codecrafters.taskhubcore.service;

import com.codecrafters.taskhubcore.controller.usuarios.dto.UserDTO;
import com.codecrafters.taskhubcore.controller.usuarios.mapper.UserWebMapper;
import com.codecrafters.taskhubcore.model.entities.JobEntity;
import com.codecrafters.taskhubcore.model.entities.UserEntity;
import com.codecrafters.taskhubcore.model.repositories.JobRepository;
import com.codecrafters.taskhubcore.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobService jobService;
    private final UserWebMapper mapper;

    public UserDTO findById(String id) {
        return mapper.toDTO(userRepository.findById(id).orElseThrow());
    }

    public UserDTO findByEmail(String email) {
        return mapper.toDTO(userRepository.findByEmail(email).orElseThrow());
    }

    public UserDTO subscribeJob(String userId, String jobId) {
        JobEntity job = jobRepository.findById(jobId).orElseThrow();
        UserEntity user = userRepository.findById(userId).orElseThrow();
        job.getSubscribersId().add(user.getId());
        user.getJobsIdSubscribed().add(job.getId());
        jobRepository.save(job);
        return mapper.toDTO(userRepository.save(user));
    }

    public UserDTO unsubscribeJob(String userId, String jobId) {
        JobEntity job = jobRepository.findById(jobId).orElseThrow();
        UserEntity user = userRepository.findById(userId).orElseThrow();
        job.getSubscribersId().remove(user.getId());
        user.getJobsIdSubscribed().remove(job.getId());
        jobRepository.save(job);
        return mapper.toDTO(userRepository.save(user));
    }

    public Boolean login(UserDTO userDTO) {
        UserEntity entity = userRepository.findByEmail(userDTO.email()).orElse(null);
        if (entity != null) {
            return entity.getPassword().equals(userDTO.password());
        } else return false;
    }

    public UserDTO save(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            return null;
        }
        return mapper.toDTO(userRepository.save(mapper.toEntity(userDTO)));
    }

    public UserDTO update(UserDTO userNew, String id) {
        UserEntity userOld = userRepository.findById(id).orElseThrow();
        userOld.setName(userNew.name() == null ? userOld.getName() : userNew.name());
        userOld.setEmail(userNew.email() == null ? userOld.getEmail() : userNew.email());
        userOld.setPassword(userNew.password() == null ? userOld.getPassword() : userNew.password());
        userOld.setPhone(userNew.phone() == null ? userOld.getPhone() : userNew.phone());
        return mapper.toDTO(userRepository.save(userOld));
    }

    public void delete(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        userEntity.getJobsIdCreated().forEach(jobService::delete);
        List<JobEntity> jobs = jobRepository.findAllById(userEntity.getJobsIdSubscribed());
        jobs.forEach(job -> job.getSubscribersId().remove(userEntity.getId()));
        jobRepository.saveAll(jobs);
        userRepository.deleteById(id);
    }
}
