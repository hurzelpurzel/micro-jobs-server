package com.andreidodu.mapper;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.dto.RoomDTO;
import com.andreidodu.model.Job;
import com.andreidodu.model.message.Message;
import com.andreidodu.model.message.Room;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper extends ModelMapperCommon<Room, RoomDTO> {

    public RoomMapper() {
        super(Room.class, RoomDTO.class);
    }
    @PostConstruct
    public void postConstruct() {
        super.getModelMapper().typeMap(Room.class, RoomDTO.class).addMappings(mapper -> {
            //mapper.<Long>map(src -> src.getParticipants().get(0).getJob().getId(), (destination, value) -> destination.setJobId(value));
        });
    }
}
