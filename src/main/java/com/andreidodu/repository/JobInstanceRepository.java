package com.andreidodu.repository;

import com.andreidodu.model.JobInstance;
import org.springframework.data.repository.CrudRepository;

public interface JobInstanceRepository extends CrudRepository<JobInstance, Long> {
}