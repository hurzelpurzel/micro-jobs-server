package com.andreidodu.mapper;

import com.andreidodu.dto.PaymentTypeDTO;
import com.andreidodu.model.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class PaymentTypeMapper extends ModelMapperCommon<PaymentType, PaymentTypeDTO> {

    public PaymentTypeMapper() {
        super(PaymentType.class, PaymentTypeDTO.class);
    }

}
