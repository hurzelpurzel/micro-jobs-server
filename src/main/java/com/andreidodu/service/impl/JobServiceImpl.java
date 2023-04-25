package com.andreidodu.service.impl;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.JobMapper;
import com.andreidodu.model.Job;
import com.andreidodu.repository.JobRepository;
import com.andreidodu.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    @Override
    public JobDTO get(Long id) throws ApplicationException {
        Optional<Job> modelOpt  =this.jobRepository.findById(id);
        if (modelOpt.isEmpty()){
            throw new ApplicationException("Job not found");
        }
        return this.jobMapper.toDTO(modelOpt.get());
    }

    @Override
    public void delete(Long id) {
        this.jobRepository.deleteById(id);
    }

    @Override
    public JobDTO save(JobDTO jobDTO) {
        final Job user = this.jobRepository.save(this.jobMapper.toModel(jobDTO));
        JobDTO JobDTOSaved = this.jobMapper.toDTO(user);
        return JobDTOSaved;
    }

    @Override
    public JobDTO update(Long id, JobDTO jobDTO) throws ApplicationException {
        if (id != jobDTO.getId()) {
            throw new ApplicationException("id not matching");
        }
        Optional<Job> userOpt = this.jobRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("job not found");
        }
        Job user = userOpt.get();
        this.jobMapper.getModelMapper().map(jobDTO, user);
        Job userSaved = this.jobRepository.save(user);
        return this.jobMapper.toDTO(userSaved);

    }

}
