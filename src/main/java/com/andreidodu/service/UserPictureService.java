package com.andreidodu.service;

import com.andreidodu.dto.UserPictureDTO;
import com.andreidodu.exception.ApplicationException;

public interface UserPictureService {
    UserPictureDTO get(Long id) throws ApplicationException;

    void delete(Long id);

    UserPictureDTO save(UserPictureDTO userPictureDTO) throws ApplicationException;

    UserPictureDTO update(Long id, UserPictureDTO userPictureDTO) throws ApplicationException;
}
