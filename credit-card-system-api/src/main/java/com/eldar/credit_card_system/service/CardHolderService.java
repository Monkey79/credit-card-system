package com.eldar.credit_card_system.service;

import java.util.List;
import java.util.Optional;

import com.eldar.credit_card_system.dto.CardHolderDto;

public interface CardHolderService {	
	List<CardHolderDto> getAllCardHolders();
	Optional<CardHolderDto> getCardHolderById(Long id);
	Optional<CardHolderDto> getCardHolderByFullName(String name, String lastName);
	
	CardHolderDto saveCardHolder(CardHolderDto cardHolderDto);
	CardHolderDto updateCardHolder(Long id, CardHolderDto cardHolderDto);
}
