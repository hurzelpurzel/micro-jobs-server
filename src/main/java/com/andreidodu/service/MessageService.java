package com.andreidodu.service;

import com.andreidodu.dto.ConversationDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.dto.MessageRequestDTO;
import com.andreidodu.dto.MessageResponseDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.exception.ValidationException;

import java.util.List;

public interface MessageService {
    MessageDTO get(Long id) throws ApplicationException;

    void delete(Long id);

    MessageDTO update(Long id, MessageDTO messageDTO) throws ApplicationException;

    List<ConversationDTO> getConversations(String extractUsernameFromAuthorizzation);

    List<MessageResponseDTO> getConversationMessages(String extractUsernameFromAuthorizzation, Long userToId, Long jobId);

    MessageResponseDTO save(String username, MessageRequestDTO messageRequestDTO) throws ValidationException;
}
