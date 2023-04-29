package com.andreidodu.repository;

import com.andreidodu.model.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Long> {
    long countByType(int type);

    long countByTypeAndPublisher_username(int type, String username);

    void deleteByIdAndPublisher_Username(Long id, String username);
}