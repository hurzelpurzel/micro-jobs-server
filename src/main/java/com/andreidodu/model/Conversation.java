package com.andreidodu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Conversation {
    private String usernameTo;
    private Long userFromId;
    private Long jobId;
    private String jobTitle;
}
