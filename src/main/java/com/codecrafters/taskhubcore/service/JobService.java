package com.codecrafters.taskhubcore.service;

import com.codecrafters.taskhubcore.model.entities.AddressEntity;
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

    public JobEntity findById(String id) {
        return jobRepository.findById(id).orElseThrow();
    }

    public JobEntity save(JobEntity jobEntity) {
        UserEntity user = userRepository.findById(jobEntity.getCrafter().getId()).orElseThrow();
        jobEntity.setId(null);
        jobEntity.setCrafter(user);
        JobEntity jobSaved = jobRepository.save(jobEntity);
        user.getJobsCreated().add(jobSaved);
        userRepository.save(user);
        return jobSaved;
    }

    public JobEntity updateAvailable(String id) {
        JobEntity jobOld = findById(id);
        jobOld.setAvailable(!jobOld.getAvailable());
        return jobRepository.save(jobOld);
    }

    public JobEntity update(JobEntity jobNew, String id) {
        JobEntity jobOld = findById(id);
        jobOld.setTitle(jobNew.getTitle() == null ? jobOld.getTitle() : jobNew.getTitle());
        jobOld.setImageUrl(jobNew.getTitle() == null ? jobOld.getImageUrl() : jobNew.getImageUrl());
        jobOld.setPayment(jobNew.getTitle() == null ? jobOld.getPayment() : jobNew.getPayment());
        jobOld.setMoment(jobNew.getMoment() == null ? jobOld.getMoment() : jobNew.getMoment());
        jobOld.setDetails(jobNew.getDetails() == null ? jobOld.getDetails() : jobNew.getDetails());

        if (jobNew.getAddress() != null) {
            AddressEntity addressEntity =AddressEntity.builder()
                    .state(jobNew.getAddress().getState() == null ? jobOld.getAddress().getState() : jobNew.getAddress().getState())
                    .city(jobNew.getAddress().getCity() == null ? jobOld.getAddress().getCity() : jobNew.getAddress().getCity())
                    .neighborhood(jobNew.getAddress().getNeighborhood() == null ? jobOld.getAddress().getNeighborhood() : jobNew.getAddress().getNeighborhood())
                    .street(jobNew.getAddress().getStreet() == null ? jobOld.getAddress().getStreet() : jobNew.getAddress().getStreet())
                    .postalCode(jobNew.getAddress().getPostalCode() == null ? jobOld.getAddress().getPostalCode() : jobNew.getAddress().getPostalCode())
                    .number(jobNew.getAddress().getNumber() == null ? jobOld.getAddress().getNumber() : jobNew.getAddress().getNumber())
                    .complement(jobNew.getAddress().getComplement() == null ? jobOld.getAddress().getComplement() : jobNew.getAddress().getComplement())
                    .build();

            jobOld.setAddress(addressEntity);
        }

        return jobRepository.save(jobOld);
    }

    public void delete(String id) {
        JobEntity job = jobRepository.findById(id).orElseThrow();
        UserEntity crafter = userRepository.findById(job.getCrafter().getId()).orElseThrow();
        crafter.getJobsCreated().remove(job);
        List<UserEntity> users = userRepository.findAll();
        users.forEach(user -> user.getJobsSubscribed().remove(job));
        userRepository.saveAll(users);
        jobRepository.deleteById(id);
    }
}
