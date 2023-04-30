package com.andreidodu.service.impl;

import com.andreidodu.constants.JobConst;
import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.JobMapper;
import com.andreidodu.model.Job;
import com.andreidodu.model.JobPicture;
import com.andreidodu.model.User;
import com.andreidodu.repository.JobPageableRepository;
import com.andreidodu.repository.JobPictureRepository;
import com.andreidodu.repository.JobRepository;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.JobService;
import com.andreidodu.validators.JobDTOValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobPageableRepository jobPageableRepository;
    private final JobPictureRepository jobPictureRepository;
    private final JobMapper jobMapper;

    @Override
    public JobDTO get(Long id) throws ApplicationException {
        Optional<Job> modelOpt = this.jobRepository.findById(id);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("Job not found");
        }

        JobDTO dto = this.jobMapper.toDTO(modelOpt.get());
        dto.setImages(new ArrayList<>());
        modelOpt.get().getJobPictureList().stream().forEach(jobPicture -> {
            dto.getImages().add(new String(jobPicture.getPicture()));
        });
        return dto;
    }

    @Override
    public List<JobDTO> getAll(int type, int page) throws ApplicationException {
        JobDTOValidator.validateJobType(type);
        Pageable secondPageWithFiveElements = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByType(type, secondPageWithFiveElements);
        List<JobDTO> jobDTOList = this.jobMapper.toListDTO(models);
        return jobDTOList;
    }

    @Override
    public List<JobDTO> getAll(String username, int type, int page) throws ApplicationException {
        JobDTOValidator.validateJobType(type);
        Pageable secondPageWithFiveElements = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByTypeAndPublisher_username(type, username, secondPageWithFiveElements);
        return this.jobMapper.toListDTO(models);
    }

    @Override
    public void delete(Long id, String username) {
        this.jobRepository.deleteByIdAndPublisher_Username(id, username);
    }

    @Override
    public JobDTO save(JobDTO jobDTO, String username) throws ApplicationException {
        jobDTO.setStatus(JobConst.STATUS_CREATED);
        Job model = this.jobMapper.toModel(jobDTO);
        Optional<User> userOpt = this.userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("User not found");
        }
        model.setPublisher(userOpt.get());
        final Job job = this.jobRepository.save(model);

        Optional.ofNullable(jobDTO.getImages()).orElse(new ArrayList<>()).stream()
                .map(base64ImageFull -> {
                    JobPicture modelJobPicture = new JobPicture();
                    modelJobPicture.setPicture(base64ImageFull.getBytes(StandardCharsets.UTF_8));
                    modelJobPicture.setJob(job);
                    return modelJobPicture;
                }).forEach(modelJobPicture -> {
                    this.jobPictureRepository.save(modelJobPicture);
                });
        return this.jobMapper.toDTO(job);
    }


    @Override
    public JobDTO approveJob(Long jobId, String owner) throws ApplicationException {
        Optional<User> userOpt = this.userRepository.findByUsername(owner);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("User not found");
        }
        Optional<Job> modelOpt = this.jobRepository.findById(jobId);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("Job not found");
        }
        modelOpt.get().setStatus(JobConst.STATUS_PUBLISHED);
        Job model = this.jobRepository.save(modelOpt.get());
        return this.jobMapper.toDTO(model);
    }

    @Override
    public JobDTO update(Long id, JobDTO jobDTO, String owner) throws ApplicationException {
        if (!id.equals(jobDTO.getId())) {
            throw new ApplicationException("id not matching");
        }
        Optional<Job> userOpt = this.jobRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("job not found");
        }
        Job user = userOpt.get();
        if (!user.getPublisher().getUsername().equals(owner)) {
            throw new ApplicationException("wrong user");
        }
        this.jobMapper.getModelMapper().map(jobDTO, user);
        Job userSaved = this.jobRepository.save(user);
        return this.jobMapper.toDTO(userSaved);

    }

    @Override
    public long countByType(int type) {
        return this.jobRepository.countByType(type);
    }

    @Override
    public long countByTypeAndUsername(String username, int type) {
        return this.jobRepository.countByTypeAndPublisher_username(type, username);
    }

}
