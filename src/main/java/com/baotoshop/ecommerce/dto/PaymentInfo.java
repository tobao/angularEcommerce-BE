package com.baotoshop.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInfo {
    private BigDecimal amount;
    private String currency;
}
