package com.eldar.credit_card_system.utils.creditcard_rate;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AmexRateStrategy implements RateStrategy {

	@Override
	public BigDecimal calculateRate(LocalDate date) {
		return BigDecimal.valueOf(date.getMonthValue() * 0.1);
	}

}
