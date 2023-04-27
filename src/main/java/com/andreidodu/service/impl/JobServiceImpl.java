package com.andreidodu.service.impl;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.JobPictureDTO;
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
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
        return this.jobMapper.toDTO(modelOpt.get());
    }

    @Override
    public List<JobDTO> getAll(int type, int page) throws ApplicationException {
        Pageable secondPageWithFiveElements = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByType(type, secondPageWithFiveElements);
        return this.jobMapper.toListDTO(models);
    }

    @Override
    public List<JobDTO> getAll(String username, int type, int page) throws ApplicationException {
        Pageable secondPageWithFiveElements = PageRequest.of(page, 10);
        List<Job> models = this.jobPageableRepository.findByTypeAndPublisher_username(type, username, secondPageWithFiveElements);
        return this.jobMapper.toListDTO(models);
    }

    @Override
    public void delete(Long id) {
        this.jobRepository.deleteById(id);
    }

    @Override
    public JobDTO save(JobDTO jobDTO, String username) throws ApplicationException {
        jobDTO.setStatus(0);
        Job model = this.jobMapper.toModel(jobDTO);
        Optional<User> userOpt = this.userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("User not found");
        }
        model.setPublisher(userOpt.get());
        final Job job = this.jobRepository.save(model);

        Optional.ofNullable(jobDTO.getImages()).orElse(new ArrayList<>()).stream()
                .map(base64ImageFull -> {
                    try {
                        String base64Image = base64ImageFull.split(",")[1];
                        JobPicture modelJobPicture = new JobPicture();
                        modelJobPicture.setPicture(ArrayUtils.toObject(Base64
                                .getDecoder()
                                .decode(new String(base64Image)
                                        .getBytes("UTF-8"))));
                        modelJobPicture.setJob(job);
                        return modelJobPicture;
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }).forEach(modelJobPicture -> {
                    this.jobPictureRepository.save(modelJobPicture);
                });


        return this.jobMapper.toDTO(job);
    }

    @Override
    public JobDTO update(Long id, JobDTO jobDTO) throws ApplicationException {
        if (!id.equals(jobDTO.getId())) {
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

    @Override
    public long countByType(int type) {
        return this.jobRepository.countByType(type);
    }

    @Override
    public long countByTypeAndUsername(String username, int type) {
        return this.jobRepository.countByTypeAndPublisher_username(type, username);
    }

}
