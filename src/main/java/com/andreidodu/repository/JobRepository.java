package com.andreidodu.repository;

import com.andreidodu.model.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long> {
    List<Job> findByType(Integer type);
}