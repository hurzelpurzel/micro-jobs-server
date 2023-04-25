package com.andreidodu.mapper;

import com.andreidodu.dto.MessageDTO;
import com.andreidodu.model.Message;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;

@Component
public class MessageMapper extends ModelMapperCommon<Message, MessageDTO> {

    public MessageMapper() {
        super(Message.class, MessageDTO.class);
    }

    @PostConstruct
    public void postConstruct() {
        super.getModelMapper().typeMap(Message.class, MessageDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUserFrom().getId(), MessageDTO::setUserFromId);
            mapper.map(src -> src.getUserTo().getId(), MessageDTO::setUserToId);
        });
    }
}
