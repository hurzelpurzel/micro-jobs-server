package com.andreidodu.service.impl;

import com.andreidodu.dto.JobPictureDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.JobPictureMapper;
import com.andreidodu.model.Job;
import com.andreidodu.model.JobPicture;
import com.andreidodu.repository.JobPictureRepository;
import com.andreidodu.repository.JobRepository;
import com.andreidodu.service.JobPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobPictureServiceImpl implements JobPictureService {

    private final JobPictureRepository jobPictureRepository;
    private final JobRepository jobRepository;

    private final JobPictureMapper jobPictureMapper;

    @Override
    public JobPictureDTO get(Long id) throws ApplicationException {
        Optional<JobPicture> modelOpt = this.jobPictureRepository.findById(id);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("JobPicture not found");
        }
        return this.jobPictureMapper.toDTO(modelOpt.get());
    }

    @Override
    public void delete(Long id) {
        this.jobPictureRepository.deleteById(id);
    }

    @Override
    public JobPictureDTO save(JobPictureDTO jobPictureDTO) throws ApplicationException {
        Optional<Job> job = this.jobRepository.findById(jobPictureDTO.getJobId());
        if (job.isEmpty()) {
            throw new ApplicationException("Job not found");
        }
        JobPicture model = this.jobPictureMapper.toModel(jobPictureDTO);
        model.setJob(job.get());
        final JobPicture user = this.jobPictureRepository.save(model);
        JobPictureDTO JobPictureDTOSaved = this.jobPictureMapper.toDTO(user);
        return JobPictureDTOSaved;
    }

    @Override
    public JobPictureDTO update(Long id, JobPictureDTO jobPictureDTO) throws ApplicationException {
        if (id != jobPictureDTO.getId()) {
            throw new ApplicationException("id not matching");
        }
        Optional<JobPicture> userOpt = this.jobPictureRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("job not found");
        }
        JobPicture jobPicture = userOpt.get();
        this.jobPictureMapper.getModelMapper().map(jobPictureDTO, jobPicture);
        JobPicture userSaved = this.jobPictureRepository.save(jobPicture);
        return this.jobPictureMapper.toDTO(userSaved);

    }

}
