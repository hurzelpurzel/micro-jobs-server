package com.andreidodu.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobInstanceDTO extends DTOCommon {

    private Long id;
    private Long userWorkerId;
    private Long userCustomerId;
    private Long jobId;
    private Integer status;


}
