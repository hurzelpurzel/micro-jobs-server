package com.andreidodu.service.impl;

import com.andreidodu.dto.PaymentTypeDTO;
import com.andreidodu.exception.ApplicationException;
import com.andreidodu.mapper.PaymentTypeMapper;
import com.andreidodu.mapper.PaymentTypeMapper;
import com.andreidodu.model.PaymentType;
import com.andreidodu.model.User;
import com.andreidodu.repository.PaymentTypeRepository;
import com.andreidodu.repository.PaymentTypeRepository;
import com.andreidodu.repository.UserRepository;
import com.andreidodu.service.PaymentTypeService;
import com.andreidodu.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    private final PaymentTypeMapper paymentTypeMapper;
    private final UserRepository userRepository;

    @Override
    public PaymentTypeDTO get(Long id) throws ApplicationException {
        Optional<PaymentType> modelOpt = this.paymentTypeRepository.findById(id);
        if (modelOpt.isEmpty()) {
            throw new ApplicationException("PaymentType not found");
        }
        return this.paymentTypeMapper.toDTO(modelOpt.get());
    }

    @Override
    public void delete(Long id) {
        this.paymentTypeRepository.deleteById(id);
    }

    @Override
    public PaymentTypeDTO save(PaymentTypeDTO paymentTypeDTO) throws ApplicationException {
        Optional<User> user = userRepository.findById(paymentTypeDTO.getUserId());
        if (user.isEmpty()) {
            throw new ApplicationException("user not found ");
        }
        PaymentType model = this.paymentTypeMapper.toModel(paymentTypeDTO);
        model.setUser(user.get());
        final PaymentType paymentType = this.paymentTypeRepository.save(model);
        return this.paymentTypeMapper.toDTO(paymentType);
    }

    @Override
    public PaymentTypeDTO update(Long id, PaymentTypeDTO paymentTypeDTO) throws ApplicationException {
        if (id != paymentTypeDTO.getId()) {
            throw new ApplicationException("id not matching");
        }
        Optional<PaymentType> userOpt = this.paymentTypeRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ApplicationException("job not found");
        }
        PaymentType user = userOpt.get();
        this.paymentTypeMapper.getModelMapper().map(paymentTypeDTO, user);
        PaymentType userSaved = this.paymentTypeRepository.save(user);
        return this.paymentTypeMapper.toDTO(userSaved);

    }

}
