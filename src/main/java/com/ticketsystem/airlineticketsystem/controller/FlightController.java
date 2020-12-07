package com.ticketsystem.airlineticketsystem.controller;

import com.ticketsystem.airlineticketsystem.model.*;
import com.ticketsystem.airlineticketsystem.repo.FlightRepository;
import com.ticketsystem.airlineticketsystem.utils.DateTimeUtil;
import com.ticketsystem.airlineticketsystem.utils.IntervalStartComparator;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



@RestController
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    /**
     * Get a flight back as JSON.
     */
    @RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFlightJson(@PathVariable("flightNumber") String flightNumber) {

        return getFlight(flightNumber);

    }

    /**
     * Get a flight back as XML.
     */
    @RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.GET, params = "xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getFlightXml(@PathVariable("flightNumber") String flightNumber,
                                          @RequestParam(value = "xml") String isXml) {
        if (isXml.equals("true")) {
            return getFlight(flightNumber);

        } else {
            return new ResponseEntity<>(new BadRequest(new Response(400, "Xml param; found in invalid state!")),
                    HttpStatus.BAD_REQUEST);
        }

    }

    private ResponseEntity<?> getFlight(String flightNumber) {
        //query db
        Flight flight = flightRepository.findOne(flightNumber);

        if (flight == null) {
            return new ResponseEntity<>(new BadRequest(new Response(404, "Sorry, the requested flight with number "
                    + flightNumber + " does not exist")), HttpStatus.NOT_FOUND);

        } else {
            // form appropriate response
            List<PassengerInfo> passengers = new ArrayList<>();
            for (Reservation reservation : flight.getReservations()) {
                passengers.add(new PassengerInfo(reservation.getPassengerFKey()));
            }
            flight.setPassengers(passengers);

            return new ResponseEntity<>(flight, HttpStatus.OK);
        }

    }

    /**
     Create or update a flight.
     */
    @RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> createOrUpdateFlight(@PathVariable("flightNumber") String flightNumber,
                                                  @RequestParam(value = "price") int price,
                                                  @RequestParam(value = "from") String from,
                                                  @RequestParam(value = "to") String to,
                                                  @RequestParam(value = "departureTime") String departureTime,
                                                  @RequestParam(value = "arrivalTime") String arrivalTime,
                                                  @RequestParam(value = "description") String description,
                                                  @RequestParam(value = "capacity") int capacity,
                                                  @RequestParam(value = "model") String model,
                                                  @RequestParam(value = "manufacturer") String manufacturer,
                                                  @RequestParam(value = "yearOfManufacture") int yearOfManufacture) {

        // to update the seatsLeft when capacity is modified
        Flight flight = flightRepository.findOne(flightNumber);
        int seatsLeft = capacity;

        if (arrivalTime.equals(departureTime)) {
            return new ResponseEntity<>(new BadRequest(new Response(400, "Arrival and Departure Time can't be same!")),
                    HttpStatus.BAD_REQUEST);
        }

        if (capacity < 0) {
            return new ResponseEntity<>(new BadRequest(new Response(400, "Capacity can't be negative value!")),
                    HttpStatus.BAD_REQUEST);
        }

        if (flight != null && capacity < flight.getReservations().size()) {
            return new ResponseEntity<>(new BadRequest(new Response(400, "New capacity can't be less than " +
                    "the reservations for that flight!")),
                    HttpStatus.BAD_REQUEST);
        }

        if (flight != null) {
            // set flight capacity
            seatsLeft = capacity - flight.getReservations().size();

            price += flight.getPrice();

            int k = (int)((flight.getCapacity())*(10.0f/100.0f));

            if (flight.getSeatsLeft() % k == 0) {
                price  = (int) (flight.getPrice() * (110.0f / 100.0f));
            }


            // placeholder
            List<Interval> intervals = new ArrayList<>();

            for (Reservation reservation : flight.getReservations()) {

                Passenger passenger = reservation.getPassengerFKey();
                List<Reservation> passengerReservations = passenger.getReservations();

                for (Reservation passengerReservation : passengerReservations) {
                    List<Flight> flights = passengerReservation.getFlights();

                    for (Flight passengerFlight : flights) {
                        // compute interval
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh", Locale.US);

                        try {
                            Date departureDateTime = simpleDateFormat.parse(passengerFlight.getDepartureTime());
                            Date arrivalDateTime = simpleDateFormat.parse(passengerFlight.getArrivalTime());

                            intervals.add(new Interval(departureDateTime.getTime(), arrivalDateTime.getTime()));

                        } catch (ParseException e) {
                            e.printStackTrace();
                            return new ResponseEntity<>(new BadRequest(new Response(500, "Invalid Date Format!")),
                                    HttpStatus.BAD_REQUEST);

                        }

                    }
                }

            }

            Collections.sort(intervals, new IntervalStartComparator());

            // check Time-Overlap for a certain passenger
            if (DateTimeUtil.isOverlapping(intervals)) {
                return new ResponseEntity<>(new BadRequest(new Response(400, "Passenger will have overlapping flight time!")),
                        HttpStatus.BAD_REQUEST);
            }

        }

        // save to db
        Flight flightObjFromDb = flightRepository.save(new Flight(flightNumber, price, from, to, departureTime,
                arrivalTime, description, seatsLeft, capacity, new Plane(model, manufacturer, yearOfManufacture)));

        return new ResponseEntity<>(flightObjFromDb, HttpStatus.OK);

    }

    /**
     * Delete a flight.
     */
    @RequestMapping(value = "/airline/{flightNumber}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFlight(@PathVariable("flightNumber") String flightNumber) {

        // query db
        Flight flight = flightRepository.findOne(flightNumber);

        if (flight == null) {
            return new ResponseEntity<>(new BadRequest(new Response(404, "Flight with number " +
                    flightNumber + " does not exist")), HttpStatus.NOT_FOUND);

        } else if (!flight.getReservations().isEmpty()) {
            return new ResponseEntity<>(new BadRequest(new Response(400, "Flight with number " + flightNumber
                    + " has one or more reservation")), HttpStatus.BAD_REQUEST);

        } else {
            // remove from db
            flightRepository.delete(flightNumber);
            return new ResponseEntity<>(new Response(200, "Flight with number " + flightNumber +
                    " is deleted successfully"), HttpStatus.OK);
        }

    }

}