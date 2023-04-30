package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.model.Job;
import com.andreidodu.model.JobPicture;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class JobMapper extends ModelMapperCommon<Job, JobDTO> {

    public JobMapper() {
        super(Job.class, JobDTO.class);
    }

    Condition<List<JobPicture>, String> notNull = ctx -> ctx.getSource() != null;
    Converter<List<JobPicture>, String> converter = ctx -> {
        var listOfPictures = Optional.ofNullable(ctx.getSource())
                .orElse(new ArrayList<>());
        if (!listOfPictures.isEmpty()) {
            return listOfPictures.iterator()
                    .next()
                    .getPictureName();
        }
        return null;
    };

    @PostConstruct
    public void postConstruct() {
        super.getModelMapper().typeMap(Job.class, JobDTO.class).addMappings(mapper -> {
            mapper.<Long>map(src -> src.getPublisher().getId(), (destination, value) -> destination.getAuthor().setId(value));
            mapper.<String>map(src -> src.getPublisher().getFirstname(), (destination, value) -> destination.getAuthor().setFirstname(value));
            mapper.<String>map(src -> src.getPublisher().getLastname(), (destination, value) -> destination.getAuthor().setLastname(value));
            mapper.<String>map(src -> src.getPublisher().getUsername(), (destination, value) -> destination.getAuthor().setUsername(value));
            mapper.<Integer>map(src -> src.getPublisher().getRating(), (destination, value) -> destination.getAuthor().setStars(value));
            mapper.when(notNull).using(converter).<String>map(src -> src.getJobPictureList(), JobDTO::setPictureName);
        });
    }

}
