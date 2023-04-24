package com.andreidodu.repository;

import com.andreidodu.model.Job;
import com.andreidodu.model.User;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Long> {
}