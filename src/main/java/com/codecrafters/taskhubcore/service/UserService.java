package com.codecrafters.taskhubcore.service;

import com.codecrafters.taskhubcore.model.entities.JobEntity;
import com.codecrafters.taskhubcore.model.entities.UserEntity;
import com.codecrafters.taskhubcore.model.repositories.JobRepository;
import com.codecrafters.taskhubcore.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public UserEntity findById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public UserEntity subscribeJob(String userId, String jobId) {
        JobEntity jobEntity = jobRepository.findById(jobId).orElseThrow();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        userEntity.getJobsSubscribed().add(jobEntity);
        return userRepository.save(userEntity);
    }

    public UserEntity unsubscribeJob(String userId, String jobId) {
        JobEntity jobEntity = jobRepository.findById(jobId).orElseThrow();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        userEntity.getJobsSubscribed().remove(jobEntity);
        return userRepository.save(userEntity);
    }

    public Boolean login(UserEntity userEntity) {
        UserEntity entity = userRepository.findByEmail(userEntity.getEmail()).orElse(null);
        if (entity != null) {
            return entity.getPassword().equals(userEntity.getPassword());
        } else return false;
    }

    public UserEntity save(UserEntity userEntity) {
        userEntity.setId(null);
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            return null;
        }
        return userRepository.save(userEntity);
    }

    public UserEntity update(UserEntity userNew, String id) {
        UserEntity userOld = findById(id);
        userOld.setName(userNew.getName() == null ? userOld.getName() : userNew.getName());
        userOld.setEmail(userNew.getEmail() == null ? userOld.getEmail() : userNew.getEmail());
        userOld.setPassword(userNew.getPassword() == null ? userOld.getPassword() : userNew.getPassword());
        return userRepository.save(userOld);
    }

    public void delete(String id) {
        userRepository.delete(findById(id));
    }
}
