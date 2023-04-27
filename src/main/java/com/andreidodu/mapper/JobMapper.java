package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.model.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper extends ModelMapperCommon<Job, JobDTO> {

    public JobMapper() {
        super(Job.class, JobDTO.class);
    }

}
