package com.andreidodu.service;

import com.andreidodu.dto.JobInstanceDTO;
import com.andreidodu.exception.ApplicationException;

public interface JobInstanceService {
    JobInstanceDTO get(Long id) throws ApplicationException;

    void delete(Long id);

    JobInstanceDTO save(JobInstanceDTO jobDTO) throws ApplicationException;

    JobInstanceDTO update(Long id, JobInstanceDTO jobDTO) throws ApplicationException;
}
