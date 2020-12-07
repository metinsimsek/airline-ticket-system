package com.ticketsystem.airlineticketsystem.repo;

import com.ticketsystem.airlineticketsystem.model.Passenger;
import com.ticketsystem.airlineticketsystem.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional(Transactional.TxType.REQUIRED)
public interface PaymentRepository extends CrudRepository<Payment, String> {

    @Query(value = "SELECT DISTINCT * FROM passenger ps, payment p WHERE ps.id = p.passenger_id AND " +
            "p.credit_card_number = :credit_card_number",
            nativeQuery = true)
    Payment getPaymentByCreditCardNumber(@Param("credit_card_number") String credit_card_number);

}
