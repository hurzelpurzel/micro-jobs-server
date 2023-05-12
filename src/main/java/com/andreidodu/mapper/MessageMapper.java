package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.model.Job;
import com.andreidodu.model.message.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper extends ModelMapperCommon<Message, MessageDTO> {

    public MessageMapper() {
        super(Message.class, MessageDTO.class);
    }

    @PostConstruct
    public void postConstruct() {
        super.getModelMapper().typeMap(Message.class, MessageDTO.class).addMappings(mapper -> {
            mapper.<String>map(src -> src.getUser().getUsername(), (destination, value) -> destination.setUsername(value));
        });
    }
}
