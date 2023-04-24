package com.andreidodu.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDTO extends DTOCommon {

    private Long id;
    private String title;
    private String description;
    private Integer type;
    private Integer status;


}
