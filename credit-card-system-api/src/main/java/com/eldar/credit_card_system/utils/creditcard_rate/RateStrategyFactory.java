package com.eldar.credit_card_system.utils.creditcard_rate;

import java.util.Map;

public class RateStrategyFactory {
	private final Map<String, RateStrategy> strategiesMp;
	
	public RateStrategyFactory() {
        this.strategiesMp = Map.of(
            "VISA", new VisaRateStrategy(),
            "NARA", new NaraRateStrategy(),
            "AMEX", new AmexRateStrategy()
        );
    }
	
	public RateStrategy getStrategy(String brand) {
        RateStrategy strategy = strategiesMp.get(brand.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Marca no implementada: " + brand);
        }
        return strategy;
    }
}
