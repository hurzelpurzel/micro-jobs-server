package com.andreidodu.service;

import com.andreidodu.dto.RatingDTO;
import com.andreidodu.exception.ApplicationException;

public interface RatingService {
    RatingDTO get(Long id) throws ApplicationException;

    void delete(Long id);

    RatingDTO save(RatingDTO ratingDTO) throws ApplicationException;

    RatingDTO update(Long id, RatingDTO ratingDTO) throws ApplicationException;
}
