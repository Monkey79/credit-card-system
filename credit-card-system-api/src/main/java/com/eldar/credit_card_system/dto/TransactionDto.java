package com.eldar.credit_card_system.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class TransactionDto {

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal amount;

    @NotBlank(message = "El detalle de la transacción no puede estar vacío")
    private String detail;

    private LocalDate transactionDate;

    @NotNull(message = "El id de la tarjeta de crédito no puede ser nulo")
    private Long cardId;
    
    private CreditCardDto creditCardDto;
}

