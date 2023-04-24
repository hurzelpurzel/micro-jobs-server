package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO extends DTOCommon{

    private Long id;
    private Long userVoterId;
    private Long userTargetId;
    private Long jobInstanceId;
    private String comment;
    private Integer rating;
}
