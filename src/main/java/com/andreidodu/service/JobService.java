package com.andreidodu.service;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;

import java.util.List;

public interface JobService {
    JobDTO getPrivate(final Long id, final String username) throws ApplicationException;

    List<JobDTO> getAll(int type, int page) throws ApplicationException;

    List<JobDTO> getAll(String username, int type, int page) throws ApplicationException;

    void delete(Long id, String username) throws ApplicationException;

    JobDTO save(JobDTO jobDTO, String username) throws ApplicationException;

    JobDTO approveJob(Long jobId, String owner) throws ApplicationException;

    JobDTO update(Long id, JobDTO jobDTO, String owner) throws ApplicationException;

    long countByType(int type);

    long countByTypeAndUsername(String username, int type);

    JobDTO getPublic(Long id) throws ApplicationException;
}
