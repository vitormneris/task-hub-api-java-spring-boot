package com.codecrafters.taskhubcore.repositories;

import com.codecrafters.taskhubcore.model.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, String> {
}
