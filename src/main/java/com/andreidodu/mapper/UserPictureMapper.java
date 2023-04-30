package com.andreidodu.mapper;

import com.andreidodu.dto.UserPictureDTO;
import com.andreidodu.mapper.common.ConverterCommon;
import com.andreidodu.model.UserPicture;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class UserPictureMapper extends ModelMapperCommon<UserPicture, UserPictureDTO> {

    public UserPictureMapper() {
        super(UserPicture.class, UserPictureDTO.class);
    }

    @PostConstruct
    private void postConstruct() {

//        getModelMapper().typeMap(UserPictureDTO.class, UserPicture.class).addMappings(mapper -> {
//            mapper.using(ConverterCommon.STRING_TO_BYTES).<Byte[]>map(UserPictureDTO::getPicture, UserPicture::setPicture);
//        });
//
//        getModelMapper().typeMap(UserPicture.class, UserPictureDTO.class).addMappings(mapper -> {
//            mapper.using(ConverterCommon.BYTES_TO_STRING).<String>map(UserPicture::getPicture, UserPictureDTO::setPicture);
//            mapper.map(src -> src.getUser().getId(), UserPictureDTO::setUserId);
//        });
    }

}
