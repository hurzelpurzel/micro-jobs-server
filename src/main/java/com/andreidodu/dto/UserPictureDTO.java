package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPictureDTO extends DTOCommon {

    private Long id;
    private Long userId;
    private String pictureName;
}
