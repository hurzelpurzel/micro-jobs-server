package com.andreidodu.mapper;

import com.andreidodu.dto.UserDTO;
import com.andreidodu.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ModelMapperCommon<User, UserDTO> {

    public UserMapper() {
        super(User.class, UserDTO.class);
    }

}
