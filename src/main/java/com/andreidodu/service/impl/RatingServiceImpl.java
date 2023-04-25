package com.andreidodu.service.impl;


import com.andreidodu.dto.RatingDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.RatingMapper;
import com.andreidodu.model.JobInstance;
import com.andreidodu.model.Rating;
import com.andreidodu.model.User;
import com.andreidodu.repository.JobInstanceRepository;
import com.andreidodu.repository.RatingRepository;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final JobInstanceRepository jobInstanceRepository;
    private final RatingMapper ratingMapper;

    @Override
    public RatingDTO get(Long id) throws ApplicationException {
        Optional<Rating> modelOpt = this.ratingRepository.findById(id);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("Rating not found");
        }
        return this.ratingMapper.toDTO(modelOpt.get());
    }

    @Override
    public void delete(Long id) {
        this.ratingRepository.deleteById(id);
    }

    @Override
    public RatingDTO save(RatingDTO ratingDTO) throws ApplicationException {
        if (ratingDTO.getUserTargetId() == null) {
            throw new ApplicationException("User target id is null");
        }
        if (ratingDTO.getUserVoterId() == null) {
            throw new ApplicationException("User voter id is null");
        }
        if (ratingDTO.getJobInstanceId() == null) {
            throw new ApplicationException("Job instance id is null");
        }
        if (ratingDTO.getUserVoterId().equals(ratingDTO.getUserTargetId())) {
            throw new ApplicationException("Voter matches with Target");
        }
        Optional<User> userTargetOpt = this.userRepository.findById(ratingDTO.getUserTargetId());
        if (userTargetOpt.isEmpty()) {
            throw new ApplicationException("Target not found");
        }
        Optional<User> userVoterOpt = this.userRepository.findById(ratingDTO.getUserVoterId());
        if (userVoterOpt.isEmpty()) {
            throw new ApplicationException("Voter not found");
        }
        Optional<JobInstance> jobInstanceOpt = this.jobInstanceRepository.findById(ratingDTO.getJobInstanceId());
        if (jobInstanceOpt.isEmpty()) {
            throw new ApplicationException("JobInstance not found");
        }

        Rating rating = this.ratingMapper.toModel(ratingDTO);
        rating.setUserTarget(userTargetOpt.get());
        rating.setUserVoter(userVoterOpt.get());
        rating.setJobInstance(jobInstanceOpt.get());
        final Rating ratingSaved = this.ratingRepository.save(rating);
        return this.ratingMapper.toDTO(ratingSaved);
    }

    @Override
    public RatingDTO update(Long id, RatingDTO ratingDTO) throws ApplicationException {
        if (!id.equals(ratingDTO.getId())) {
            throw new ApplicationException("id not matching");
        }
        Optional<Rating> ratingOpt = this.ratingRepository.findById(id);
        if (ratingOpt.isEmpty()) {
            throw new ApplicationException("Rating not found");
        }
        Rating rating = ratingOpt.get();
        this.ratingMapper.getModelMapper().map(ratingDTO, rating);
        Rating userSaved = this.ratingRepository.save(rating);
        return this.ratingMapper.toDTO(userSaved);

    }

}
