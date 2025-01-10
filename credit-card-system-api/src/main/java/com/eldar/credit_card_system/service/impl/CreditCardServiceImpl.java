package com.eldar.credit_card_system.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eldar.credit_card_system.dto.CardHolderDto;
import com.eldar.credit_card_system.dto.CreditCardDto;
import com.eldar.credit_card_system.entities.CardHolder;
import com.eldar.credit_card_system.entities.CreditCard;
import com.eldar.credit_card_system.exceptions.ResourceNotFoundException;
import com.eldar.credit_card_system.repository.CreditCardRepository;
import com.eldar.credit_card_system.service.CardHolderService;
import com.eldar.credit_card_system.service.CreditCardService;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private CardHolderService cardHolderService;

	@Override
	public List<CreditCardDto> getAllCreditCards() {
		Iterable<CreditCard> creditCards = creditCardRepository.findAll();
		return StreamSupport.stream(creditCards.spliterator(), false).map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public Optional<CreditCardDto> getCreditCardById(Long id) {
		return creditCardRepository
				.findById(id).map(this::toDto).or(() -> {
					throw new ResourceNotFoundException("La tarjeta con ID " + id + " no fue encontrada.");
				});
	}

	@Override
	public CreditCardDto saveCreditCard(CreditCardDto creditCardDto) {
		CreditCard creditCard = toEntity(creditCardDto);
		CreditCard savedCreditCard = null;
		savedCreditCard = creditCardRepository.save(creditCard);			
		return toDto(savedCreditCard);
	}

	@Override
	public CreditCardDto updateCreditCard(Long id, CreditCardDto creditCardDto) {
		CreditCard existingCreditCard = creditCardRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tarjeta de crédito no encontrada con ID " + id));
		existingCreditCard.setCardNumber(creditCardDto.getCardNumber());
		existingCreditCard.setBrand(creditCardDto.getBrand());
		existingCreditCard.setExpirationDate(creditCardDto.getExpirationDate());
		existingCreditCard.setCvv(creditCardDto.getCvv());
		CreditCard updatedCreditCard = creditCardRepository.save(existingCreditCard);
		return toDto(updatedCreditCard);
	}
	
	@Override
	public List<CreditCardDto> getCreditCardByDni(String dni) {
		return creditCardRepository
				.findByDni(dni).get().stream().map(this::toDto).toList();
	}

	// ----------------replace with a Mapper
	private CreditCardDto toDto(CreditCard creditCard) {
		CreditCardDto creditCardDto = new CreditCardDto();
		creditCardDto.setCardNumber(creditCard.getCardNumber());
		creditCardDto.setBrand(creditCard.getBrand());
		creditCardDto.setExpirationDate(creditCard.getExpirationDate());
		creditCardDto.setCvv(creditCard.getCvv());
		creditCardDto.setCardHolderId(creditCard.getCardHolderId());
		return creditCardDto;
	}

	private CreditCard toEntity(CreditCardDto creditCardDto) {
		CreditCard creditCard = new CreditCard();
		creditCard.setCardNumber(creditCardDto.getCardNumber());
		creditCard.setBrand(creditCardDto.getBrand());
		creditCard.setExpirationDate(creditCardDto.getExpirationDate());
		creditCard.setCvv(creditCardDto.getCvv());
		creditCard.setCardHolderId(creditCardDto.getCardHolderId());
		return creditCard;
	}

}
