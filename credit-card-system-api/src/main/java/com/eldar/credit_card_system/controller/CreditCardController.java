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

import com.eldar.credit_card_system.dto.CreditCardDto;
import com.eldar.credit_card_system.service.CreditCardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/credit-cards")
public class CreditCardController {
	@Autowired
	private CreditCardService creditCardService;
	
    @GetMapping("/")
    public ResponseEntity<List<CreditCardDto>> getAllCreditCards() {
        List<CreditCardDto> creditCards = creditCardService.getAllCreditCards();
        return new ResponseEntity<>(creditCards, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDto> getCreditCardById(@PathVariable("id") Long id) {
        Optional<CreditCardDto> creditCardDto = creditCardService.getCreditCardById(id);
        return creditCardDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/by-dni/{dni}")
    public ResponseEntity<List<CreditCardDto>> getCreditCardById(@PathVariable("dni") String dni) {
        List<CreditCardDto> creditCardsDto = creditCardService.getCreditCardByDni(dni);
        if (creditCardsDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(creditCardsDto);
    }
    

    @PostMapping("/")
    public ResponseEntity<CreditCardDto> createCreditCard(@RequestBody @Valid CreditCardDto creditCardDto) {
        CreditCardDto savedCreditCard = creditCardService.saveCreditCard(creditCardDto);
        return new ResponseEntity<>(savedCreditCard, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCardDto> updateCreditCard(@PathVariable("id") Long id,
                                                          @RequestBody @Valid CreditCardDto creditCardDto) {
        CreditCardDto updatedCreditCard = creditCardService.updateCreditCard(id, creditCardDto);
        return new ResponseEntity<>(updatedCreditCard, HttpStatus.OK);
    }
}
