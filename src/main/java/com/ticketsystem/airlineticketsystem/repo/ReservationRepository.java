package com.ticketsystem.airlineticketsystem.repo;

import com.ticketsystem.airlineticketsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRED)
public interface ReservationRepository extends CrudRepository<Reservation, String> {

    @Query(value = "SELECT DISTINCT * FROM passenger p, reservation r, flight_reservation fr, flight f " +
            "WHERE p.id = r.id AND r.order_no = fr.res_order_id AND fr.flyt_no = f.flight_no ",
            nativeQuery = true)
    List<Reservation> searchForReservations(@Param("passengerId") String passengerId, @Param("dest_from") String dest_from,
                                            @Param("dest_to") String dest_to, @Param("flightNumber") String flightNumber);
}
