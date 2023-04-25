package com.andreidodu.mapper;

import com.andreidodu.dto.JobPictureDTO;
import com.andreidodu.model.JobPicture;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class JobPictureMapper extends ModelMapperCommon<JobPicture, JobPictureDTO> {

    public JobPictureMapper() {
        super(JobPicture.class, JobPictureDTO.class);
    }

    @PostConstruct
    private void postConstruct() {

        getModelMapper().typeMap(JobPictureDTO.class, JobPicture.class).addMappings(mapper -> {
            mapper.using(ConverterCommon.STRING_TO_BYTES).<Byte[]>map(JobPictureDTO::getPicture, JobPicture::setPicture);
        });

        getModelMapper().typeMap(JobPicture.class, JobPictureDTO.class).addMappings(mapper -> {
            mapper.using(ConverterCommon.BYTES_TO_STRING).<String>map(JobPicture::getPicture, JobPictureDTO::setPicture);
            mapper.map(src -> src.getJob().getId(), JobPictureDTO::setJobId);
        });
    }

}
