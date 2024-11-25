package com.codecrafters.taskhubcore.model.repositories;

import com.codecrafters.taskhubcore.model.entities.JobEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<JobEntity, String> {
}
