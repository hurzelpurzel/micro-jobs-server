package com.andreidodu.service;


import com.andreidodu.dto.UserDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.UserMapper;
import com.andreidodu.model.User;
import com.andreidodu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDTO get(Long id) {
        return this.userMapper.toDTO(this.userRepository.findById(id).get());
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        final User user = this.userRepository.save(this.userMapper.toModel(userDTO));
        UserDTO userDTOSaved = this.userMapper.toDTO(user);
        return userDTOSaved;
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) throws ApplicationException {
        if (id != userDTO.getId()) {
            throw new ApplicationException("id not matching");
        }
        Optional<User> userOpt = this.userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("user not found");
        }
        User user = userOpt.get();
        this.userMapper.getModelMapper().map(userDTO, user);
        User userSaved = this.userRepository.save(user);
        return this.userMapper.toDTO(userSaved);

    }

}
