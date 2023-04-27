package com.andreidodu.mapper;

import com.andreidodu.dto.JobInstanceDTO;
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
        super.getModelMapper().typeMap(JobInstance.class, JobInstanceDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUserWorker().getId(), JobInstanceDTO::setUserWorkerId);
            mapper.map(src -> src.getUserCustomer().getId(), JobInstanceDTO::setUserCustomerId);
            mapper.map(src -> src.getJob().getId(), JobInstanceDTO::setJobId);
        });
    }
}
