package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private Integer stars;
}
