package com.eldar.credit_card_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eldar.credit_card_system.entities.CreditCard;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard,Long> {
	@Query("SELECT cc.cc_id, cc.cc_card_number, cc.cc_brand, cc.cc_expiration_date, cc.cc_cvv, cc.cc_card_holder_id\r\n"
			+ "        FROM credit_cards cc\r\n"
			+ "        INNER JOIN card_holder ch ON cc.cc_card_holder_id = ch.ch_id\r\n"
			+ "        WHERE ch.ch_dni = :dni")
	Optional<List<CreditCard> > findByDni(@Param("dni") String dni);
}
