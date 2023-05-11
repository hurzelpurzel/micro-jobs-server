package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.dto.RoomDTO;
import com.andreidodu.model.Job;
import com.andreidodu.model.JobPicture;
import com.andreidodu.model.message.Message;
import com.andreidodu.model.message.Room;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class RoomMapper extends ModelMapperCommon<Room, RoomDTO> {

    public RoomMapper() {
        super(Room.class, RoomDTO.class);
    }

    @PostConstruct
    public void postConstruct() {
        super.getModelMapper().typeMap(Room.class, RoomDTO.class).addMappings(mapper -> {
            // mapper.<String>map(src -> extractImage(src.getJob().getJobPictureList()), (destination, value) -> destination.setPictureName(value));
        });
    }


}
