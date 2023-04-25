package com.andreidodu.service;

import com.andreidodu.dto.PaymentTypeDTO;
import com.andreidodu.exception.ApplicationException;

public interface PaymentTypeService {
    PaymentTypeDTO get(Long id) throws ApplicationException;

    void delete(Long id);

    PaymentTypeDTO save(PaymentTypeDTO paymentTypeDTO) throws ApplicationException;

    PaymentTypeDTO update(Long id, PaymentTypeDTO paymentTypeDTO) throws ApplicationException;
}
