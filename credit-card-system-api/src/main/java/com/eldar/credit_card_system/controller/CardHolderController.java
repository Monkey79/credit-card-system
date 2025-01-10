package com.eldar.credit_card_system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eldar.credit_card_system.dto.CardHolderDto;
import com.eldar.credit_card_system.service.CardHolderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/card-holders")
public class CardHolderController {
	@Autowired
	private CardHolderService cardHolderService;

	@GetMapping(value = "/")
	public ResponseEntity<List<CardHolderDto>> getAllCardHolders() {
		return ResponseEntity.ok(cardHolderService.getAllCardHolders());
	}

	@GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<CardHolderDto> getCardHolderByNameAndLastName(@PathVariable String firstName,@PathVariable String lastName) {
		Optional<CardHolderDto> cardHolderDto = cardHolderService.getCardHolderByFullName(firstName,lastName);
		return cardHolderDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CardHolderDto> getCardHolderById(@PathVariable Long id) {
		Optional<CardHolderDto> cardHolderDto = cardHolderService.getCardHolderById(id);
		return cardHolderDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/")
	public ResponseEntity<CardHolderDto> createCardHolder(@RequestBody @Valid CardHolderDto cardHolderDto) {
		CardHolderDto savedCardHolder = cardHolderService.saveCardHolder(cardHolderDto);
		return new ResponseEntity<>(savedCardHolder, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CardHolderDto> updateCardHolder(@PathVariable Long id,@RequestBody @Valid CardHolderDto cardHolderDto) {
		CardHolderDto updatedCardHolder = cardHolderService.updateCardHolder(id, cardHolderDto);
		return new ResponseEntity<>(updatedCardHolder, HttpStatus.OK);
	}
}
