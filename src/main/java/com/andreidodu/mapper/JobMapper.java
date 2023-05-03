package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.model.Job;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JobMapper extends ModelMapperCommon<Job, JobDTO> {

    public JobMapper() {
        super(Job.class, JobDTO.class);
    }

    @PostConstruct
    public void postConstruct() {
        super.getModelMapper().typeMap(Job.class, JobDTO.class).addMappings(mapper -> {
            mapper.<Long>map(src -> src.getPublisher().getId(), (destination, value) -> destination.getAuthor().setId(value));
            mapper.<String>map(src -> src.getPublisher().getFirstname(), (destination, value) -> destination.getAuthor().setFirstname(value));
            mapper.<String>map(src -> src.getPublisher().getLastname(), (destination, value) -> destination.getAuthor().setLastname(value));
            mapper.<String>map(src -> src.getPublisher().getUsername(), (destination, value) -> destination.getAuthor().setUsername(value));
            mapper.<Integer>map(src -> src.getPublisher().getRating(), (destination, value) -> destination.getAuthor().setStars(value));
        });

        super.getModelMapper().typeMap(JobDTO.class, Job.class).addMappings(mapper ->{
            mapper.skip(Job::setJobPictureList);
        });
    }

}
