package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPictureDTO extends DTOCommon {

    private Long id;
    private Long jobId;
    private String pictureName;
    private String content;
}
