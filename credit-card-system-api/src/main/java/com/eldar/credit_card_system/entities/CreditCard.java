package com.eldar.credit_card_system.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("credit_cards")
public class CreditCard {
	@Id
	@Column(value = "cc_id")
	private Long id;

	@Column("cc_card_number")
	private String cardNumber;

	@Column("cc_brand")
	private String brand;

	@Column("cc_expiration_date")
	private LocalDate expirationDate;

	@Column("cc_cvv")
	private String cvv;

	@Column("cc_card_holder_id")
	private Long cardHolderId;
}
