package com.ticketsystem.airlineticketsystem.repo;

import com.ticketsystem.airlineticketsystem.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional(Transactional.TxType.REQUIRED)
public interface FlightRepository extends CrudRepository<Flight, String> {
}
