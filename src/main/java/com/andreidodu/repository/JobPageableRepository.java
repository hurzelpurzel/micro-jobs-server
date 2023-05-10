package com.andreidodu.repository;

import com.andreidodu.model.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JobPageableRepository extends PagingAndSortingRepository<Job, Long> {
    List<Job> findByTypeAndPublisher_username(int type, String username, Pageable pageable);

    List<Job> findByTypeAndStatusIn(int type, List<Integer> statuses, Pageable pageable);
}