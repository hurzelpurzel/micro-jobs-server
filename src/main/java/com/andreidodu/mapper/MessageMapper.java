package com.andreidodu.mapper;

import com.andreidodu.dto.MessageDTO;
import com.andreidodu.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper extends ModelMapperCommon<Message, MessageDTO> {

    public MessageMapper() {
        super(Message.class, MessageDTO.class);
    }

}
