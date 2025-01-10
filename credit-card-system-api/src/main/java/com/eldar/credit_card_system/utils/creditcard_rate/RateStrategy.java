package com.eldar.credit_card_system.utils.creditcard_rate;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RateStrategy {	
	BigDecimal calculateRate(LocalDate date);
}
