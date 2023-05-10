package com.andreidodu.repository;

import com.andreidodu.model.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends CrudRepository<Job, Long> {
    long countByTypeAndPublisher_username(int type, String username);

    void deleteByIdAndPublisher_Username(Long id, String username);

    Optional<Job> findByIdAndStatus(Long id, int status);

    long countByTypeAndStatusIn(int type, List<Integer> statuses);
}