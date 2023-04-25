package com.andreidodu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTypeDTO extends DTOCommon {
    private Long id;
    private String paypalEmail;
    private Long userId;
    private Integer type;
}
