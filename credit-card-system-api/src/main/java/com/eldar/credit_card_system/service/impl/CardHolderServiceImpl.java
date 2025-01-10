package com.eldar.credit_card_system.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eldar.credit_card_system.dto.CardHolderDto;
import com.eldar.credit_card_system.entities.CardHolder;
import com.eldar.credit_card_system.exceptions.ResourceNotFoundException;
import com.eldar.credit_card_system.repository.CardHolderRepository;
import com.eldar.credit_card_system.service.CardHolderService;

@Service
public class CardHolderServiceImpl implements CardHolderService {
	@Autowired
	private CardHolderRepository cardHolderRepository;

	@Override
	public List<CardHolderDto> getAllCardHolders() {
		return StreamSupport.stream(cardHolderRepository.findAll().spliterator(), false).map(this::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<CardHolderDto> getCardHolderById(Long id) {
		return cardHolderRepository.
				findById(id).map(this::toDto).or(() -> {
					throw new ResourceNotFoundException("El titular con ID " + id + " no fue encontrado.");
				});
	}

	@Override
	public CardHolderDto saveCardHolder(CardHolderDto cardHolderDto) {
        CardHolder cardHolder = toEntity(cardHolderDto);
        CardHolder savedCardHolder = cardHolderRepository.save(cardHolder);
        return toDto(savedCardHolder);
	}
	@Override
    public CardHolderDto updateCardHolder(Long id, CardHolderDto cardHolderDto) {
        CardHolder existingCardHolder = cardHolderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El titular con ID " + id + " no fue encontrado."));

        existingCardHolder.setFirstName(cardHolderDto.getFirstName());
        existingCardHolder.setLastName(cardHolderDto.getLastName());
        existingCardHolder.setDni(cardHolderDto.getDni());
        existingCardHolder.setDateOfBirth(cardHolderDto.getDateOfBirth());
        existingCardHolder.setEmail(cardHolderDto.getEmail());

        CardHolder updatedCardHolder = cardHolderRepository.save(existingCardHolder);

        return toDto(updatedCardHolder);
    }

	@Override
	public Optional<CardHolderDto> getCardHolderByFullName(String name, String lastName) {
		return cardHolderRepository.
				getByFirstNameAndLastName(name, lastName).map(this::toDto).or(() -> {
					throw new ResourceNotFoundException("El titular con nombre " + name + "y apellido "+lastName+" no fue encontrado.");
				});
	}

	//----------------Mapper(replace to mapper libs)---------------------------
	private CardHolderDto toDto(CardHolder cardHolder) {
		CardHolderDto dto = new CardHolderDto();
		dto.setCrdHldId(cardHolder.getId());
		dto.setFirstName(cardHolder.getFirstName());
		dto.setLastName(cardHolder.getLastName());
		dto.setDni(cardHolder.getDni());
		dto.setDateOfBirth(cardHolder.getDateOfBirth());
		dto.setEmail(cardHolder.getEmail());
		return dto;
	}

	private CardHolder toEntity(CardHolderDto dto) {
		CardHolder cardHolder = new CardHolder();
		cardHolder.setFirstName(dto.getFirstName());
		cardHolder.setLastName(dto.getLastName());
		cardHolder.setDni(dto.getDni());
		cardHolder.setDateOfBirth(dto.getDateOfBirth());
		cardHolder.setEmail(dto.getEmail());
		return cardHolder;
	}
}
