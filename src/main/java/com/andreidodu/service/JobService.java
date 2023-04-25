package com.andreidodu.service;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;

import java.util.List;

public interface JobService {
    JobDTO get(Long id) throws ApplicationException;

    List<JobDTO> getAll(int type, int page) throws ApplicationException;

    void delete(Long id);

    JobDTO save(JobDTO jobDTO);

    JobDTO update(Long id, JobDTO jobDTO) throws ApplicationException;
}
