package com.andreidodu.mapper;

import com.andreidodu.dto.RoomDTO;
import com.andreidodu.dto.RoomExtendedDTO;
import com.andreidodu.model.message.Room;
import com.andreidodu.model.message.RoomExtended;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RoomExtendedMapper extends ModelMapperCommon<RoomExtended, RoomExtendedDTO> {

    public RoomExtendedMapper() {
        super(RoomExtended.class, RoomExtendedDTO.class);
    }

}
