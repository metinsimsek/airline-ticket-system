package com.ticketsystem.airlineticketsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Entity
@Table(name = "reservation")
public class Reservation {

    @XmlElement
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "order_no")
    private String orderNumber;

    @XmlElement
    @Column
    private int price;

    @XmlElement
    @ManyToMany
    @JoinTable(name = "flight_reservation",
            joinColumns = {@JoinColumn(name = "res_order_id", referencedColumnName = "order_no")},
            inverseJoinColumns = {@JoinColumn(name = "flyt_no", referencedColumnName = "flight_no")})
    private List<Flight> flights;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Passenger passengerFKey;

    @XmlElement
    @Transient
    private PassengerInfo passenger;

    public Reservation() {
    }

    public Reservation(int price, Passenger passengerFKey, List<Flight> flights) {
        this.price = price;
        this.passengerFKey = passengerFKey;
        this.flights = flights;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Passenger getPassengerFKey() {
        return passengerFKey;
    }

    public void setPassengerFKey(Passenger passengerFKey) {
        this.passengerFKey = passengerFKey;
    }

    public PassengerInfo getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerInfo passenger) {
        this.passenger = passenger;
    }
}
