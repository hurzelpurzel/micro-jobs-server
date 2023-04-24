package com.andreidodu.mapper;

import com.andreidodu.dto.RatingDTO;
import com.andreidodu.model.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper extends ModelMapperCommon<Rating, RatingDTO> {

    public RatingMapper() {
        super(Rating.class, RatingDTO.class);
    }

}
