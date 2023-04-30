package com.andreidodu.mapper;

import com.andreidodu.dto.JobPictureDTO;
import com.andreidodu.mapper.common.ConverterCommon;
import com.andreidodu.model.JobPicture;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class JobPictureMapper extends ModelMapperCommon<JobPicture, JobPictureDTO> {

    public JobPictureMapper() {
        super(JobPicture.class, JobPictureDTO.class);
    }


}
