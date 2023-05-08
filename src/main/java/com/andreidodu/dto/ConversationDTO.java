package com.andreidodu.dto;

import com.andreidodu.model.Job;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConversationDTO {
    private LocalDateTime creationDate;
    private Long userToId;
    private String usernameTo;
    private Long userFromId;
    private String usernameFrom;
    private Long jobId;
    private String jobTitle;
}
