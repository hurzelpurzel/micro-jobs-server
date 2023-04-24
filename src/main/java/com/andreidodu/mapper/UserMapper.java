package com.andreidodu.mapper;

import com.andreidodu.dto.UserDTO;
import com.andreidodu.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ModelMapperCommon<User, UserDTO> {

    public UserMapper() {
        super(User.class, UserDTO.class);
    }

    @PostConstruct
    public void postConstruct() {
     
    }
}
