package com.andreidodu.service.impl;


import com.andreidodu.dto.UserDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.UserMapper;
import com.andreidodu.model.User;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDTO get(Long id) throws ApplicationException {
        Optional<User> modelOpt = this.userRepository.findById(id);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("User not found");
        }
        return this.userMapper.toDTO(modelOpt.get());
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        final User user = this.userRepository.save(this.userMapper.toModel(userDTO));
        return this.userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) throws ApplicationException {
        if (!id.equals(userDTO.getId())) {
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
