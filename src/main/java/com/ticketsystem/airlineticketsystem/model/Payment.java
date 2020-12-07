package com.ticketsystem.airlineticketsystem.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "payment")
public class Payment {

    @XmlElement
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "payment_id")
    private String payment_id;

    @XmlElement
    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private Passenger passengerFKey;

    @XmlElement
    @Transient
    private PassengerInfo passenger;

    public Payment() {
    }

    public Payment(String creditCardNumber, Passenger passengerFKey) {
        this.creditCardNumber = creditCardNumber;
        this.passengerFKey = passengerFKey;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
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
