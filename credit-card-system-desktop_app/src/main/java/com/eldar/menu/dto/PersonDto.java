package com.eldar.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {	
	private Long crdHldId;
	
	private String firstName;	
	private String lastName;	
	private String dni;	
	private String dateOfBirth;    
	private String email;	
}


