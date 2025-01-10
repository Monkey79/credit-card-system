package com.eldar.credit_card_system.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eldar.credit_card_system.entities.CardHolder;

@Repository
public interface CardHolderRepository extends CrudRepository<CardHolder, Long> {
    @Query("SELECT * FROM card_holder WHERE ch_first_name = :firstName AND ch_last_name = :lastName")
	Optional<CardHolder> getByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
