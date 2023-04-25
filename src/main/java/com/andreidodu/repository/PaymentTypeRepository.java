package com.andreidodu.repository;

import com.andreidodu.model.Job;
import com.andreidodu.model.PaymentType;
import org.springframework.data.repository.CrudRepository;

public interface PaymentTypeRepository extends CrudRepository<PaymentType, Long> {
}