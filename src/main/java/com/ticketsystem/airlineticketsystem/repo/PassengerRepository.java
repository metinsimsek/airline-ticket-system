package com.ticketsystem.airlineticketsystem.repo;

import com.ticketsystem.airlineticketsystem.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


@Transactional(Transactional.TxType.REQUIRED)
public interface PassengerRepository extends CrudRepository<Passenger, String> {

    @Query(value = "SELECT DISTINCT * FROM passenger p, reservation r WHERE p.id = r.id AND r.order_no = :orderNumber",
            nativeQuery = true)
    Passenger getPassengerByOrderNo(@Param("orderNumber") String orderNumber);
}
