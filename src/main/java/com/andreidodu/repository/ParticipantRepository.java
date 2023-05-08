package com.andreidodu.repository;

import com.andreidodu.model.message.Participant;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Long>, QuerydslPredicateExecutor<Participant> {

}