package com.eldar.menu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ApiFormatterDate {

	public static String dateBirthApiFormatAndValidation(String dateOfBirth) {
		if (dateOfBirth == null || dateOfBirth.isBlank()) {
			throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía o nula.");
		}
		
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter outPutFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate birthDate = LocalDate.parse(dateOfBirth, inputFormatter);
			if (birthDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha de nacimiento no puede ser una fecha futura.");					
			if (birthDate.isBefore(LocalDate.of(1900, 1, 1))) throw new IllegalArgumentException("La fecha de nacimiento no puede ser anterior al año 1900.");
			
			String formattedDate = birthDate.format(outPutFormatter);			
			return formattedDate;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("La fecha de nacimiento no tiene el formato válido (dd-MM-yyyy).", e);
		}
	}
	
	public static String expirationDateApiFormatAndValidation(String expDate) {
		if (expDate == null || expDate.isBlank()) {
			throw new IllegalArgumentException("La fecha de expieracion no puede estar vacía o nula.");
		}
		
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter outPutFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate birthDate = LocalDate.parse(expDate, inputFormatter);
			if (birthDate.isBefore(LocalDate.of(2025, 1, 1))) throw new IllegalArgumentException("La fecha de expieracion no puede ser anterior al año 2025.");
			
			String formattedDate = birthDate.format(outPutFormatter);			
			return formattedDate;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("La fecha de expiracion no tiene el formato válido (dd-MM-yyyy).", e);
		}
	}
}
