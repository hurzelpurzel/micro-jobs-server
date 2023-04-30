package com.andreidodu.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserProfileDTO {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String description;
    private Integer status;
    private Double stars;
    private UserPictureDTO picture;
}
