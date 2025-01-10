package com.eldar.credit_card_system.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("transactions") 
public class Transaction {
	@Id
    private Long tsId;

    @Column(value = "ts_amount")
    private BigDecimal amount;

    @Column(value = "ts_detail")
    private String detail;

    @Column(value = "ts_transaction_date")
    private LocalDate transactionDate;

    @Column(value = "ts_card_id")
    private Long cardId;
}
