package com.eldar.credit_card_system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eldar.credit_card_system.entities.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
