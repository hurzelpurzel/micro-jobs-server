package com.andreidodu.dto;

import com.andreidodu.model.Job;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationDTO {
    private String usernameTo;
    private Long userFromId;
    private Long userToId;
    private Long jobId;
    private String jobTitle;
}
