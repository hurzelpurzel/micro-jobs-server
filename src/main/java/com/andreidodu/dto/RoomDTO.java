package com.andreidodu.dto;

import com.andreidodu.model.Job;
import com.andreidodu.model.message.Message;
import com.andreidodu.model.message.Participant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDTO {
    private Long id;
    private String title;
    private String description;
    private Integer status;
    private Long jobId;
}
