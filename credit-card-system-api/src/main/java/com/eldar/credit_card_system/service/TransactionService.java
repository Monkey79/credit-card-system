package com.eldar.credit_card_system.service;

import java.util.List;
import java.util.Optional;

import com.eldar.credit_card_system.dto.RateDto;
import com.eldar.credit_card_system.dto.TransactionDto;
import com.eldar.credit_card_system.entities.CreditCard;

public interface TransactionService {
	TransactionDto saveTransaction(TransactionDto transactionDto);
	Optional<TransactionDto> getTransactionById(Long id);
	List<TransactionDto> getAllTransactions();
	TransactionDto updateTransaction(Long id, TransactionDto transactionDto);
	
	Optional<CreditCard> findCreditCarForTransaction(Long trsCardId);
	RateDto calculateRate(Long id, TransactionDto transactionDto);
}
