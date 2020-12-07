package com.ticketsystem.airlineticketsystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "flight")
public class Flight {

    @XmlElement
    @Id
    @Column(name = "flight_no")
    private String flightNumber;

    @XmlElement
    @Column(name = "ticket_price")
    private int price;

    @XmlElement
    @Column(name = "dest_from")
    private String from;

    @XmlElement
    @Column(name = "dest_to")
    private String to;

    @XmlElement
    @Column(name = "departure_time")
    private String departureTime;

    @XmlElement
    @Column(name = "arrival_time")
    private String arrivalTime;

    @XmlElement
    @Column
    private String description;

    @XmlElement
    @Embedded
    private Plane plane;

    @ManyToMany(mappedBy = "flights")
    private List<Reservation> reservations;

    @XmlElement
    @Column(name = "seats_left")
    private int seatsLeft;

    @XmlElement
    @Column(name = "capacity")
    private int capacity;

    //    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@passengers")
    @XmlElement
    @Transient
    private List<PassengerInfo> passengers;

/**
 * Instantiates a new Flight.
 */

    public Flight() {
    }

    public Flight(String flightNumber, int price, String from, String to, String departureTime, String arrivalTime,
                  String description, int seatsLeft,int capacity, Plane plane) {
        this.flightNumber = flightNumber;
        this.price = price;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.description = description;
        this.seatsLeft = seatsLeft;
        this.capacity = capacity;
        this.plane = plane;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void incrementPrice() {
        price *= (110.0f / 100.0f);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    @JsonIgnore
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void incrementSeatsLeftByOne() {
        seatsLeft += 1;
    }
    public boolean decrementSeatsLeftByOne() {

        return --seatsLeft == -1;
    }

    public List<PassengerInfo> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerInfo> passengers) {
        this.passengers = passengers;
    }
}