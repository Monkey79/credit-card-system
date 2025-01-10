package com.eldar.credit_card_system.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("card_holder") 
public class CardHolder {
    @Id
    @Column(value = "ch_id")
    private Long id;
    @Column(value = "ch_first_name")
    private String firstName;
    @Column(value = "ch_last_name")
    private String lastName;
    @Column(value = "ch_dni")
    private String dni;
    @Column(value = "ch_date_of_birth")
    private LocalDate dateOfBirth;
    @Column("ch_email")
    private String email;
}
