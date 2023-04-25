package com.andreidodu.service;

import com.andreidodu.dto.MessageDTO;
import com.andreidodu.exception.ApplicationException;

public interface MessageService {
    MessageDTO get(Long id) throws ApplicationException;

    void delete(Long id);

    MessageDTO save(MessageDTO messageDTO) throws ApplicationException;

    MessageDTO update(Long id, MessageDTO messageDTO) throws ApplicationException;
}
