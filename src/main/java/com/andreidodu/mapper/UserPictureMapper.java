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

}
