package com.eldar.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {
	private String brand;	
	private String cardNumber;
	private String expirationDate;
	private String cardHolderName;
	private String cardHolderLastName;
	private String cvv; 
	private Long cardHolderId;
}
