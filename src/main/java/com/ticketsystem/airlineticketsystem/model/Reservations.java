package com.ticketsystem.airlineticketsystem.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class Reservations {

    private List<Reservation> reservation;

    public Reservations() {
    }

    public Reservations(List<Reservation> reservation) {
        this.reservation = reservation;

    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }
}
