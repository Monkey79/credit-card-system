package com.eldar.credit_card_system.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreditCardDto {
	@NotNull(message = "El número de tarjeta es obligatorio")
	@NotBlank(message = "El número de tarjeta no puede estar vacío")
	@Pattern(regexp = "^[0-9]{16}$", message = "El número de tarjeta debe tener 16 dígitos")
	private String cardNumber;

	@NotNull(message = "La marca de la tarjeta es obligatoria")
	private String brand;

	@NotNull(message = "La fecha de vencimiento es obligatoria")
	private LocalDate expirationDate;

	@NotNull(message = "El CVV es obligatorio")
	@Pattern(regexp = "^[0-9]{3}$", message = "El CVV debe tener 3 dígitos")
	private String cvv;

	@NotNull(message = "El ID del titular es obligatorio")
	private Long cardHolderId;
	
	
	private String cardHolderName;	
	private String cardHolderLastName;
}
