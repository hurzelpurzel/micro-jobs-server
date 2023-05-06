package com.andreidodu.service.impl;

import com.andreidodu.dto.ConversationDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.dto.MessageRequestDTO;
import com.andreidodu.dto.MessageResponseDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.exception.ValidationException;
import com.andreidodu.mapper.ConversationMapper;
import com.andreidodu.mapper.MessageMapper;
import com.andreidodu.mapper.MessageResponseMapper;
import com.andreidodu.model.Conversation;
import com.andreidodu.model.Job;
import com.andreidodu.model.Message;
import com.andreidodu.model.User;
import com.andreidodu.repository.JobRepository;
import com.andreidodu.repository.MessageRepository;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    private final MessageResponseMapper messageResponseMapper;

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
    public MessageResponseDTO save(String username, MessageRequestDTO messageRequestDTO) throws ValidationException {
        if (messageRequestDTO.getUserToId() == null) {
            throw new ValidationException("User to is null");
        }
        Optional<User> userFromOpt = this.userRepository.findByUsername(username);
        if (userFromOpt.isEmpty()) {
            throw new ValidationException("User from not found");
        }
        if (messageRequestDTO.getUserToId().equals(userFromOpt.get().getId())) {
            throw new ValidationException("You cannot write a message to yourself");
        }
        Optional<User> userToOpt = this.userRepository.findById(messageRequestDTO.getUserToId());
        if (userToOpt.isEmpty()) {
            throw new ValidationException("User to not found");
        }
        Optional<Job> jobOpt = this.jobRepository.findById(messageRequestDTO.getJobId());
        if (jobOpt.isEmpty()) {
            throw new ValidationException("job not found");
        }
        Message model = new Message();
        model.setMessage(messageRequestDTO.getMessage());
        model.setJob(jobOpt.get());
        model.setUserFrom(userFromOpt.get());
        model.setUserTo(userToOpt.get());
        model.setStatus(1);
        final Message message = this.messageRepository.save(model);
        return this.messageResponseMapper.toDTO(message);
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

    @Override
    public List<ConversationDTO> getConversations(String username) {
        List<Conversation> conversationDTOS = messageRepository.findByUsername(username);
        return conversationMapper.toListDTO(conversationDTOS);
    }

    @Override
    public List<MessageResponseDTO> getConversationMessages(String extractUsernameFromAuthorizzation, Long userToId, Long jobId) {
        Optional<User> owner = this.userRepository.findByUsername(extractUsernameFromAuthorizzation);
        List<Message> messages = this.messageRepository.findByJobUserToUserFrom(Arrays.asList(owner.get().getId(), userToId), jobId);
        return this.messageResponseMapper.toListDTO(messages);
    }


}
