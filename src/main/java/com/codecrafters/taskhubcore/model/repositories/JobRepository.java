package com.codecrafters.taskhubcore.model.repositories;

import com.codecrafters.taskhubcore.model.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, String> {
}
