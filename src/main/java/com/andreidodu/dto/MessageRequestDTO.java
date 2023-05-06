package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDTO  {
    private Long userToId;
    private String message;
    private Long jobId;
}
