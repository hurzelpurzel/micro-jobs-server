package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.model.Job;
import com.andreidodu.model.message.Message;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper extends ModelMapperCommon<Message, MessageDTO> {

    public MessageMapper() {
        super(Message.class, MessageDTO.class);
    }

    Converter<byte[], String> byteToStringConverter = (i) -> {
        return new String(i.getSource());
    };

    @PostConstruct
    public void postConstruct() {
        super.getModelMapper().typeMap(Message.class, MessageDTO.class).addMappings(mapper -> {
            mapper.<String>map(src -> src.getUser().getUsername(), (destination, value) -> destination.setUsername(value));
            mapper.<String>map(src -> src.getUser().getUserPicture().getPictureName(), (destination, value) -> destination.setPictureName(value));
        });
    }
}
