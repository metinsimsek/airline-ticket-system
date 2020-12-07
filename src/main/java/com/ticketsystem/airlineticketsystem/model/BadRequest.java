package com.ticketsystem.airlineticketsystem.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class BadRequest {

    private Response response;

    public BadRequest() {
    }

    public BadRequest(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}