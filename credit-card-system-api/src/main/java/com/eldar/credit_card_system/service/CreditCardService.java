package com.eldar.credit_card_system.service;

import java.util.List;
import java.util.Optional;

import com.eldar.credit_card_system.dto.CreditCardDto;

public interface CreditCardService {
	List<CreditCardDto> getAllCreditCards();
	Optional<CreditCardDto> getCreditCardById(Long id);
	List<CreditCardDto> getCreditCardByDni(String dni);
	CreditCardDto saveCreditCard(CreditCardDto creditCardDto);
	CreditCardDto updateCreditCard(Long id, CreditCardDto creditCardDto);
}
