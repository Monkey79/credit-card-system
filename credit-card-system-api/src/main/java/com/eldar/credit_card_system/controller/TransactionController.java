package com.eldar.credit_card_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eldar.credit_card_system.dto.RateDto;
import com.eldar.credit_card_system.dto.TransactionDto;
import com.eldar.credit_card_system.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@GetMapping(value = "/")
	public ResponseEntity<List<TransactionDto>> getAllTransactions() {
		List<TransactionDto> transactions = transactionService.getAllTransactions();
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
	@PostMapping(value = "/rate/{id}")
	public ResponseEntity<RateDto> getRateByTransaction(@PathVariable Long id,@RequestBody @Validated TransactionDto transactionDto ){
		RateDto response = transactionService.calculateRate(id,transactionDto);
        return ResponseEntity.ok(response);		
	}

}
