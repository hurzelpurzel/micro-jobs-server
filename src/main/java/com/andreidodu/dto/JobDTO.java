package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class JobDTO extends DTOCommon {

    private Long id;
    private String title;
    private String description;
    private Integer type;
    private Integer status;
    private Double price;
    private AuthorDTO author = new AuthorDTO();
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<JobPictureDTO> jobPictureList;
    private String picture;
}
