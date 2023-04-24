package com.andreidodu.service;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.UserDTO;
import com.andreidodu.exception.ApplicationException;

public interface JobService {
    JobDTO get(Long id) throws ApplicationException;

    void delete(Long id);

    JobDTO save(JobDTO jobDTO);

    JobDTO update(Long id, JobDTO jobDTO) throws ApplicationException;
}
