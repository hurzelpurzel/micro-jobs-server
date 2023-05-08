package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO extends DTOCommon {
    private Long id;
    private Long roomId;
    private Long jobId;
    private Long userFromId;
    private Long userToId;
    private String message;
    private Integer status;
}
