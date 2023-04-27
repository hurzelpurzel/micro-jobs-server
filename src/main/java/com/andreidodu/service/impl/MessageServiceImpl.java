package com.andreidodu.service.impl;

import com.andreidodu.dto.MessageDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.MessageMapper;
import com.andreidodu.model.Message;
import com.andreidodu.model.User;
import com.andreidodu.repository.MessageRepository;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private final MessageMapper messageMapper;

    @Override
    public MessageDTO get(Long id) throws ApplicationException {
        Optional<Message> modelOpt = this.messageRepository.findById(id);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("Message not found");
        }
        return this.messageMapper.toDTO(modelOpt.get());
    }

    @Override
    public void delete(Long id) {
        this.messageRepository.deleteById(id);
    }

    @Override
    public MessageDTO save(MessageDTO messageDTO) throws ApplicationException {
        if (messageDTO.getUserFromId() == null) {
            throw new ApplicationException("User from is null");
        }
        if (messageDTO.getUserToId() == null) {
            throw new ApplicationException("User to is null");
        }
        if (messageDTO.getUserFromId().equals(messageDTO.getUserToId())) {
            throw new ApplicationException("User from can not match with user to");
        }
        Optional<User> userFromOpt = this.userRepository.findById(messageDTO.getUserFromId());
        if (userFromOpt.isEmpty()) {
            throw new ApplicationException("User from not found");
        }
        Optional<User> userToOpt = this.userRepository.findById(messageDTO.getUserToId());
        if (userToOpt.isEmpty()) {
            throw new ApplicationException("User to not found");
        }
        Message model = this.messageMapper.toModel(messageDTO);
        model.setUserFrom(userFromOpt.get());
        model.setUserTo(userToOpt.get());
        final Message message = this.messageRepository.save(model);
        return this.messageMapper.toDTO(message);
    }

    @Override
    public MessageDTO update(Long id, MessageDTO messageDTO) throws ApplicationException {
        if (!id.equals(messageDTO.getId())) {
            throw new ApplicationException("id not matching");
        }
        if (messageDTO.getUserFromId() == null) {
            throw new ApplicationException("User from is null");
        }
        if (messageDTO.getUserToId() == null) {
            throw new ApplicationException("User to is null");
        }
        if (messageDTO.getUserFromId().equals(messageDTO.getUserToId())) {
            throw new ApplicationException("User from can not match with user to");
        }
        Optional<Message> messageOpt = this.messageRepository.findById(id);
        if (messageOpt.isEmpty()) {
            throw new ApplicationException("message not found");
        }
        Message message = messageOpt.get();
        this.messageMapper.getModelMapper().map(messageDTO, message);
        Message userSaved = this.messageRepository.save(message);
        return this.messageMapper.toDTO(userSaved);
    }

}
