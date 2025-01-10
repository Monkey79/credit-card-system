package com.eldar.credit_card_system.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Data
public class CardHolderDto {
	private Long crdHldId;
	
	@NotNull(message = "El nombre no puede ser nulo")
	@NotBlank(message = "El nombre no puede estar vacío")
	private String firstName;
	@NotNull(message = "El apellido no puede ser nulo")
	@NotBlank(message = "El apellido no puede estar vacío")
	private String lastName;
	
	@NotNull(message = "El DNI no puede ser nulo")
	@NotBlank(message = "El DNI no puede estar vacío")
	private String dni;
	
	@NotNull(message = "La fecha de nacimiento es obligatoria")
	@Past(message = "La fecha de nacimiento debe ser almenos un dia antes del actual")
	private LocalDate dateOfBirth;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no tiene un formato válido")
	private String email;
}
