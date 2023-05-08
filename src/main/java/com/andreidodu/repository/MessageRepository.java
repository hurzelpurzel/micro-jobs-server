package com.andreidodu.repository;

import com.andreidodu.model.Conversation;
import com.andreidodu.model.message.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long>, QuerydslPredicateExecutor<Message> {

}