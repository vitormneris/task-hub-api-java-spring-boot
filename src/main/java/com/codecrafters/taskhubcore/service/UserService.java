package com.codecrafters.taskhubcore.service;

import com.codecrafters.taskhubcore.model.entities.JobEntity;
import com.codecrafters.taskhubcore.model.entities.UserEntity;
import com.codecrafters.taskhubcore.model.repositories.JobRepository;
import com.codecrafters.taskhubcore.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

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
        JobEntity job = jobRepository.findById(jobId).orElseThrow();
        UserEntity user = userRepository.findById(userId).orElseThrow();
        if (user.getJobsSubscribed() == null) user.setJobsSubscribed(new HashSet<>());
        if (job.getSubscribers() == null) job.setSubscribers(new HashSet<>());
        job.getSubscribers().add(user);
        user.getJobsSubscribed().add(job);
        return userRepository.save(user);
    }

    public UserEntity unsubscribeJob(String userId, String jobId) {
        JobEntity job = jobRepository.findById(jobId).orElseThrow();
        UserEntity user = userRepository.findById(userId).orElseThrow();
        if (user.getJobsSubscribed() == null) user.setJobsSubscribed(new HashSet<>());
        if (job.getSubscribers() == null) job.setSubscribers(new HashSet<>());
        job.getSubscribers().remove(user);
        user.getJobsSubscribed().remove(job);
        return userRepository.save(user);
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
        userOld.setPhone(userNew.getPhone() == null ? userOld.getPhone() : userNew.getPhone());
        return userRepository.save(userOld);
    }

    public void delete(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        List<String> jobsId = userEntity.getJobsSubscribed().stream().map(JobEntity::getId).toList();
        List<JobEntity> jobs = jobRepository.findAllById(jobsId);
        jobs.forEach(job -> job.getSubscribers().remove(userEntity));
        jobRepository.saveAll(jobs);
        userRepository.deleteById(id);
    }
}
