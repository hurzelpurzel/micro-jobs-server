package com.andreidodu.mapper;

import com.andreidodu.dto.MessageDTO;
import com.andreidodu.dto.MessageResponseDTO;
import com.andreidodu.model.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MessageResponseMapper extends ModelMapperCommon<Message, MessageResponseDTO> {

    public MessageResponseMapper() {
        super(Message.class, MessageResponseDTO.class);
    }

    @PostConstruct
    public void postConstruct() {
        super.getModelMapper().typeMap(Message.class, MessageResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUserFrom().getUsername(), MessageResponseDTO::setUsernameFrom);
            mapper.map(src -> src.getUserTo().getUsername(), MessageResponseDTO::setUsernameTo);
            mapper.map(src -> src.getCreatedDate(), MessageResponseDTO::setDate);
        });
    }
}
