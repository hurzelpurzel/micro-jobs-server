package com.andreidodu.service.impl;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.JobMapper;
import com.andreidodu.model.Job;
import com.andreidodu.model.User;
import com.andreidodu.repository.JobPageableRepository;
import com.andreidodu.repository.JobRepository;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobPageableRepository jobPageableRepository;

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
        final Job user = this.jobRepository.save(model);
        return this.jobMapper.toDTO(user);
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
