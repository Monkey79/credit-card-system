package com.eldar.credit_card_system.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eldar.credit_card_system.dto.CreditCardDto;
import com.eldar.credit_card_system.dto.RateDto;
import com.eldar.credit_card_system.dto.TransactionDto;
import com.eldar.credit_card_system.entities.CreditCard;
import com.eldar.credit_card_system.entities.Transaction;
import com.eldar.credit_card_system.exceptions.ResourceNotFoundException;
import com.eldar.credit_card_system.repository.TransactionRepository;
import com.eldar.credit_card_system.service.CreditCardService;
import com.eldar.credit_card_system.service.TransactionService;
import com.eldar.credit_card_system.utils.creditcard_rate.RateStrategy;
import com.eldar.credit_card_system.utils.creditcard_rate.RateStrategyFactory;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private CreditCardService creditCardService;
	
	private final RateStrategyFactory rateStrategyFactory;
	
	public TransactionServiceImpl() {
		rateStrategyFactory = new RateStrategyFactory();
	}

	@Override
	public TransactionDto saveTransaction(TransactionDto transactionDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TransactionDto> getTransactionById(Long id) {
		return transactionRepository.
				findById(id).map(this::toDto).or(() -> {
					throw new ResourceNotFoundException("La transaccion con ID " + id + " no fue encontrada.");
				});
	}

	@Override
	public List<TransactionDto> getAllTransactions() {
		Iterable<Transaction> transactions = transactionRepository.findAll();
		List<TransactionDto> transactionDtos = new ArrayList<>();
		transactions.forEach((trns)->{
			TransactionDto trsDto = toDto(trns);
			Optional<CreditCardDto> crdCardDto = creditCardService.getCreditCardById(trns.getCardId());
			trsDto.setCreditCardDto(crdCardDto.get());
			
			transactionDtos.add(trsDto);
		});
		return transactionDtos;
	}

	@Override
	public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
		// TODO Auto-generated method stub
		return null;
	}	

	@Override
	public Optional<CreditCard> findCreditCarForTransaction(Long trsCardId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public RateDto calculateRate(Long id, TransactionDto transactionDto) {
		Optional<TransactionDto> trnsDto = getTransactionById(id);
		Optional<CreditCardDto> crdCardDto = creditCardService.getCreditCardById(trnsDto.get().getCardId());
		
		String brand = crdCardDto.get().getBrand();
        RateStrategy strategy = rateStrategyFactory.getStrategy(brand);
        LocalDate transactionDate = trnsDto.get().getTransactionDate();
        BigDecimal rate = strategy.calculateRate(transactionDate);
        
        RateDto rateDto = new RateDto();	
        rateDto.setCardBrand(crdCardDto.get().getBrand());
        rateDto.setTotalAmount(trnsDto.get().getAmount().multiply(rate));
        
        return rateDto;
	}
	

	//-------replace with a Mapper
	private TransactionDto toDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDetail(transaction.getDetail());
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        transactionDto.setCardId(transaction.getCardId());
        return transactionDto;
    }
	private Transaction toEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDetail(transactionDto.getDetail());
        transaction.setTransactionDate(transactionDto.getTransactionDate());
        transaction.setCardId(transactionDto.getCardId());
        
        return transaction;
    }

}
