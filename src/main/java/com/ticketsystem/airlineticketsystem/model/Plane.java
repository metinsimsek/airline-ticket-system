package com.ticketsystem.airlineticketsystem.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Embeddable
public class Plane {

    @Column
    private String model;

    @Column
    private String manufacturer;

    @Column(name = "year_of_manufacture")
    private int yearOfManufacture;


    public Plane() {
    }

    public Plane(String model, String manufacturer, int yearOfManufacture) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }
}
