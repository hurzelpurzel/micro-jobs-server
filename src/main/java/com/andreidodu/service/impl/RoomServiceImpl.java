package com.andreidodu.service.impl;

import com.andreidodu.dto.MessageDTO;
import com.andreidodu.dto.RoomDTO;
import com.andreidodu.dto.RoomExtendedDTO;
import com.andreidodu.exception.ValidationException;
import com.andreidodu.mapper.MessageMapper;
import com.andreidodu.mapper.RoomExtendedMapper;
import com.andreidodu.mapper.RoomMapper;
import com.andreidodu.model.Job;
import com.andreidodu.model.User;
import com.andreidodu.model.message.Message;
import com.andreidodu.model.message.Participant;
import com.andreidodu.model.message.Room;
import com.andreidodu.repository.*;
import com.andreidodu.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomCrudRepository roomCrudRepository;
    private final JobRepository jobRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final ParticipantRepository participantRepository;
    private final RoomMapper roomMapper;
    private final RoomExtendedMapper roomExtendedMapper;

    @Override
    public MessageDTO createMessage(String username, MessageDTO messageDTO) {
        Optional<Room> roomOptional = roomCrudRepository.findById(messageDTO.getRoomId());
        User user = userRepository.findByUsername(username).get();
        Room room = roomOptional.get();

        Message message = new Message();
        message.setMessage(messageDTO.getMessage());
        message.setStatus(1);
        message.setUser(user);
        message.setRoom(room);
        messageRepository.save(message);

        return this.messageMapper.toDTO(message);
    }

    @Override
    public RoomDTO getRoom(String username, Long jobId) {
        Optional<Room> roomOpt = roomRepository.findByJobIdAndParticipants(jobId, username);
        User user = userRepository.findByUsername(username).get();

        Room room = null;
        if (roomOpt.isEmpty()) {
            Job job = jobRepository.findById(jobId).get();
            room = new Room();
            room.setDescription(job.getDescription());
            room.setStatus(1);
            room.setTitle(job.getTitle());
            room = roomCrudRepository.save(room);

            Participant participant = new Participant();
            participant.setRoom(room);
            participant.setUser(user);
            participant.setJob(job);
            participantRepository.save(participant);
            participant = new Participant();
            participant.setRoom(room);
            participant.setUser(job.getPublisher());
            participant.setJob(job);
            participantRepository.save(participant);
        } else {
            room = roomOpt.get();
        }

        return this.roomMapper.toDTO(room);
    }


    @Override
    public List<MessageDTO> getMessages(String username, Long roomId) throws ValidationException {
        if (!roomRepository.userBelongsToRoom(username, roomId)){
            throw new ValidationException("wrong room id");
        }
        return this.messageMapper.toListDTO(roomRepository.findMessagesByUsernameAndRoomId(username, roomId));
    }

    @Override
    public List<RoomExtendedDTO> getRooms(String username) {
        return roomExtendedMapper.toListDTO(roomRepository.findRoomsByUsername(username));
    }
}
