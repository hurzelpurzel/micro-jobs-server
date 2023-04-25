package com.andreidodu.service.impl;

import com.andreidodu.dto.UserPictureDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.UserPictureMapper;
import com.andreidodu.model.User;
import com.andreidodu.model.UserPicture;
import com.andreidodu.repository.UserPictureRepository;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.UserPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPictureServiceImpl implements UserPictureService {

    private final UserPictureRepository userPictureRepository;
    private final UserRepository userRepository;

    private final UserPictureMapper userPictureMapper;

    @Override
    public UserPictureDTO get(Long id) throws ApplicationException {
        Optional<UserPicture> modelOpt = this.userPictureRepository.findById(id);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("UserPicture not found");
        }
        return this.userPictureMapper.toDTO(modelOpt.get());
    }

    @Override
    public void delete(Long id) {
        this.userPictureRepository.deleteById(id);
    }

    @Override
    public UserPictureDTO save(UserPictureDTO userPictureDTO) throws ApplicationException {
        Optional<User> user = userRepository.findById(userPictureDTO.getUserId());
        if (user.isEmpty()) {
            throw new ApplicationException("user not found");
        }
        UserPicture model = this.userPictureMapper.toModel(userPictureDTO);
        model.setUser(user.get());
        final UserPicture userPicture = this.userPictureRepository.save(model);
        return this.userPictureMapper.toDTO(userPicture);
    }

    @Override
    public UserPictureDTO update(Long id, UserPictureDTO userPictureDTO) throws ApplicationException {
        if (id != userPictureDTO.getId()) {
            throw new ApplicationException("id not matching");
        }
        Optional<UserPicture> userOpt = this.userPictureRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("user picture not found");
        }
        UserPicture jobPicture = userOpt.get();
        this.userPictureMapper.getModelMapper().map(userPictureDTO, jobPicture);
        UserPicture userSaved = this.userPictureRepository.save(jobPicture);
        return this.userPictureMapper.toDTO(userSaved);

    }

}
