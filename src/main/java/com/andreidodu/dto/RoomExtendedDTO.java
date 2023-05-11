package com.andreidodu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RoomExtendedDTO {
    private Long id;
    private String title;
    private String description;
    private Long jobId;
    private String pictureName;
    private List<String> participants;
}
