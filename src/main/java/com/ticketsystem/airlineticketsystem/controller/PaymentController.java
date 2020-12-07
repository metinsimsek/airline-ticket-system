package com.ticketsystem.airlineticketsystem.controller;


import com.ticketsystem.airlineticketsystem.model.*;
import com.ticketsystem.airlineticketsystem.repo.PassengerRepository;
import com.ticketsystem.airlineticketsystem.repo.PaymentRepository;
import com.ticketsystem.airlineticketsystem.repo.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/payment")
public class PaymentController {


    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    /**
     * (1) Get a payment as JSON
     */
    @RequestMapping(value = "/{payment_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPaymentJson(@PathVariable("payment_id") String payment_id ) {

        return getPayment(payment_id);
    }

    /**
     * Get a payment as XML.
     */
    @RequestMapping(value = "/{payment_id}", method = RequestMethod.GET, params = "xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getPaymentXml(@PathVariable("payment_id") String payment_id,
                                          @RequestParam(value = "xml") String isXml) {
        if (isXml.equals("true")) {
            return getPayment(payment_id);

        } else {
            return new ResponseEntity<>(new BadRequest(new Response(400, "Xml param; found in invalid state!")),
                    HttpStatus.BAD_REQUEST);
        }

    }

    private ResponseEntity<?> getPayment(String id) {
        //query db
        Payment payment = paymentRepository.findOne(id);

        if (payment == null) {
            return new ResponseEntity<>(new BadRequest(new Response(404, "Sorry, the requested passenger with id " +
                    id + " does not exist")), HttpStatus.NOT_FOUND);

        } else {
            return new ResponseEntity<>(payment, HttpStatus.OK);

        }

    }

    /**
     * (3) Create a payment.
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPayment(@RequestParam(value = "creditCardNumber") String creditCardNumber,
                                             @RequestParam(value = "passengerId") String passengerId) {

        Payment payment = paymentRepository.findOne(passengerId);

        Passenger passenger = passengerRepository.findOne(passengerId);

        if (passenger == null) {
            return new ResponseEntity<>(new BadRequest(new Response(404, "Passenger with id " + passengerId +
                    " does not exist")), HttpStatus.NOT_FOUND);
        }

        else {
            try {

                String creditCardNumberr = new String(String.valueOf(maskCardNumber(creditCardNumber).getBody()));
                // save to db
                Payment pay = paymentRepository.save(new Payment(creditCardNumberr, passenger));
                return new ResponseEntity<>(pay, HttpStatus.OK);


            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                return new ResponseEntity<>(new BadRequest(new Response(400, "another payment with the same" +
                        " number already exists")),
                        HttpStatus.BAD_REQUEST);
            }

        }
    }


    public static ResponseEntity<?> maskCardNumber(String creditCardNumber) {

        int SIZE = 6;
        int NUMBER_OF_LASTCHARS_NOTTO_MASK = 4;

        List<Payment> payments = new ArrayList<>();

        if (creditCardNumber == null || creditCardNumber.isEmpty()) {
            return new ResponseEntity<>(new BadRequest(new Response(404, "Credit card information does not exist"
            )), HttpStatus.NOT_FOUND);
        }

        if (creditCardNumber.length() < SIZE) {
            return new ResponseEntity<>(creditCardNumber, HttpStatus.OK);
        }

        if (creditCardNumber.replaceAll("\\W", "").length() > SIZE) {
            creditCardNumber = creditCardNumber.replaceAll("\\W", "");



            int cardLength = creditCardNumber.length();

            String inner = creditCardNumber.substring(1, cardLength - NUMBER_OF_LASTCHARS_NOTTO_MASK);
            inner = inner.replaceAll("[0-9]", "#");

            creditCardNumber = creditCardNumber.charAt(0) + inner + creditCardNumber.substring(cardLength - NUMBER_OF_LASTCHARS_NOTTO_MASK);
            }
        return new ResponseEntity<>(creditCardNumber, HttpStatus.OK);
    }

}

