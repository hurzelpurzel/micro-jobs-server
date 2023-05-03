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
    private List<String> imagesContent;
    private Double price;
    private AuthorDTO author = new AuthorDTO();
    private String pictureName;
    private List<String> pictureNamesList;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;



}
