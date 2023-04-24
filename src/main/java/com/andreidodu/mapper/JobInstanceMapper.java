package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.JobInstanceDTO;
import com.andreidodu.model.Job;
import com.andreidodu.model.JobInstance;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JobInstanceMapper extends ModelMapperCommon<JobInstance, JobInstanceDTO> {

    public JobInstanceMapper() {
        super(JobInstance.class, JobInstanceDTO.class);
    }

    @PostConstruct
    public void postConstruct() {
     
    }
}
