package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponseDTO extends DTOCommon {
    private Long id;
    private String usernameFrom;
    private String  usernameTo;
    private LocalDateTime date;
    private String message;
}
