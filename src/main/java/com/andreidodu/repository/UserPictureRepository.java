package com.andreidodu.repository;

import com.andreidodu.model.UserPicture;
import org.springframework.data.repository.CrudRepository;

public interface UserPictureRepository extends CrudRepository<UserPicture, Long> {
}