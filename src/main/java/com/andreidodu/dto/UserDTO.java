package com.andreidodu.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends DTOCommon {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String descritpion;
    private Integer status;


}
