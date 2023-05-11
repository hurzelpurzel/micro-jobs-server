package com.andreidodu.service;

import com.andreidodu.dto.JobDTO;
import com.andreidodu.dto.MessageDTO;
import com.andreidodu.dto.RoomDTO;
import com.andreidodu.dto.RoomExtendedDTO;
import com.andreidodu.exception.ValidationException;

import java.util.List;

public interface RoomService {
    MessageDTO createMessage(String usernameFrom, MessageDTO messageDTO) throws ValidationException;

    RoomDTO getRoom(String username, Long jobId);

    List<MessageDTO> getMessages(String username, Long roomId) throws ValidationException;

    List<RoomExtendedDTO> getRooms(String username);

    JobDTO getJobByRoomId(String extractUsernameFromAuthorizzation, Long roomId);
}
