package com.andreidodu.service;

import com.andreidodu.dto.UserDTO;
import com.andreidodu.exception.ApplicationException;

public interface UserService {
    UserDTO get(Long id) throws ApplicationException;

    void delete(Long id);

    UserDTO save(UserDTO userDTO);

    UserDTO update(Long id, UserDTO userDTO) throws ApplicationException;
}
