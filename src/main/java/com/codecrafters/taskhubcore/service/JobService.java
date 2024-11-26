package com.codecrafters.taskhubcore.service;

import com.codecrafters.taskhubcore.controller.jobs.dto.JobDTO;
import com.codecrafters.taskhubcore.controller.jobs.mapper.JobWebMapper;
import com.codecrafters.taskhubcore.model.entities.JobEntity;
import com.codecrafters.taskhubcore.model.entities.UserEntity;
import com.codecrafters.taskhubcore.model.repositories.JobRepository;
import com.codecrafters.taskhubcore.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobWebMapper mapper;

    public List<JobDTO> findAll() {
        return mapper.toListDTO(jobRepository.findAll());
    }

    public JobDTO findById(String id) {
        return mapper.toDTO(jobRepository.findById(id).orElseThrow());
    }

    public JobDTO save(JobDTO jobDTO) {
        UserEntity user = userRepository.findById(jobDTO.crafter().id()).orElseThrow();
        JobEntity jobEntity = mapper.toEntity(jobDTO);
        jobEntity.setAvailable(true);
        JobEntity jobSaved = jobRepository.save(jobEntity);

        user.getJobsIdCreated().add(jobSaved.getId());
        userRepository.save(user);
        return mapper.toDTO(jobSaved);
    }

    public JobDTO updateAvailable(String id) {
        JobEntity job = jobRepository.findById(id).orElseThrow();
        job.setAvailable(!job.getAvailable());
        jobRepository.save(job);
        return mapper.toDTO(job);
    }

    public JobDTO update(JobDTO jobNew, String id) {
        JobEntity jobOld = jobRepository.findById(id).orElseThrow();
        jobOld.setTitle(jobNew.title() == null ? jobOld.getTitle() : jobNew.title());
        jobOld.setImageUrl(jobNew.imageUrl() == null ? jobOld.getImageUrl() : jobNew.imageUrl());
        jobOld.setPayment(jobNew.payment() == null ? jobOld.getPayment() : jobNew.payment());
        jobOld.setMoment(jobNew.moment() == null ? jobOld.getMoment() : jobNew.moment());
        jobOld.setDetails(jobNew.details() == null ? jobOld.getDetails() : jobNew.details());
        jobOld.setAddress(jobNew.address() == null ? jobOld.getAddress() : jobNew.address());

        return mapper.toDTO(jobRepository.save(jobOld));
    }

    public void delete(String id) {
        JobEntity job = jobRepository.findById(id).orElseThrow();
        UserEntity crafter = userRepository.findById(job.getCrafterId()).orElseThrow();
        crafter.getJobsIdCreated().remove(job.getId());
        userRepository.save(crafter);
        List<UserEntity> users = userRepository.findAll();
        users.forEach(user -> user.getJobsIdSubscribed().remove(job.getId()));
        userRepository.saveAll(users);
        jobRepository.deleteById(id);
    }
}
