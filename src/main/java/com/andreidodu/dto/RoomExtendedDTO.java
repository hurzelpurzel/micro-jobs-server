package com.andreidodu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RoomExtendedDTO {
    private Long id;
    private String title;
    private String description;
    private Long jobId;
}
