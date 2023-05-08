package com.andreidodu.repository;

import com.andreidodu.model.message.Message;
import com.andreidodu.model.message.Room;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RoomCrudRepository extends CrudRepository<Room, Long>, QuerydslPredicateExecutor<Room> {

}