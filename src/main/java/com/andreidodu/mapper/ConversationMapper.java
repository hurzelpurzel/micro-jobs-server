package com.andreidodu.mapper;

import com.andreidodu.dto.ConversationDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.model.Conversation;
import com.andreidodu.model.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class ConversationMapper extends ModelMapperCommon<Conversation, ConversationDTO> {

    public ConversationMapper() {
        super(Conversation.class, ConversationDTO.class);
    }

}
