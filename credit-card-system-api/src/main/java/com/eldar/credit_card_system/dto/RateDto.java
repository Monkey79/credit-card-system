package com.eldar.credit_card_system.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RateDto {
	private String cardBrand;
	private BigDecimal totalAmount;
}
