package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.UserDTO;
import com.andreidodu.model.Job;
import com.andreidodu.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JobMapper extends ModelMapperCommon<Job, JobDTO> {

    public JobMapper() {
        super(Job.class, JobDTO.class);
    }

    @PostConstruct
    public void postConstruct() {
     
    }
}
