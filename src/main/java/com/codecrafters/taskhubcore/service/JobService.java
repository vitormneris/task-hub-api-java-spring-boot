package com.codecrafters.taskhubcore.service;

import com.codecrafters.taskhubcore.controller.jobs.dto.JobDTO;
import com.codecrafters.taskhubcore.controller.jobs.mapper.JobWebMapper;
import com.codecrafters.taskhubcore.model.entities.JobEntity;
import com.codecrafters.taskhubcore.model.repositories.JobRepository;
import com.codecrafters.taskhubcore.utils.enums.RuntimeErrorEnum;
import com.codecrafters.taskhubcore.configuration.advice.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobWebMapper mapper;

    public List<JobDTO> findAll() {
        return mapper.toListDTO(jobRepository.findAll());
    }

    public JobDTO findById(String id) {
        return mapper.toDTO(jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0002)));
    }

    public JobDTO save(JobDTO jobDTO) {
        JobEntity jobEntity = mapper.toEntity(jobDTO);
        jobEntity.setAvailable(true);
        return mapper.toDTO(jobRepository.save(jobEntity));
    }

    public JobDTO updateAvailable(String id) {
        JobEntity job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0002));
        job.setAvailable(!job.getAvailable());
        return mapper.toDTO(jobRepository.save(job));
    }

    public JobDTO update(JobDTO jobNew, String id) {
        JobEntity jobOld = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RuntimeErrorEnum.ERR0002));

        jobOld.setTitle(jobNew.title() == null ? jobOld.getTitle() : jobNew.title());
        jobOld.setImageUrl(jobNew.imageUrl() == null ? jobOld.getImageUrl() : jobNew.imageUrl());
        jobOld.setPayment(jobNew.payment() == null ? jobOld.getPayment() : jobNew.payment());
        jobOld.setMoment(jobNew.moment() == null ? jobOld.getMoment() : jobNew.moment());
        jobOld.setDetails(jobNew.details() == null ? jobOld.getDetails() : jobNew.details());
        jobOld.setAddress(jobNew.address() == null ? jobOld.getAddress() : jobNew.address());

        return mapper.toDTO(jobRepository.save(jobOld));
    }

    public void delete(String id) {
        jobRepository.deleteById(id);
    }
}
