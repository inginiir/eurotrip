package com.kalita.projects.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kalita.projects.domain.TravelNote;

import java.util.*;


@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonFromAviaSales {

    private Boolean success;
    @JsonAlias("data")
    private FlightTicket[] request;

    public JsonFromAviaSales() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public FlightTicket[] getRequest() {
        return request;
    }

    public void setRequest(FlightTicket[] request) {
        this.request = request;
    }

    public FlightTicket getCheapestFlightTicket(TravelNote travelNote, Date date) throws NoSuchElementException {
        if (this.request.length == 0) return new FlightTicket(
                0L,
                "Sorry, flight ticket not found",
                0,
                0L,
                0L,
                "-",
                new Date(),
                false,
                travelNote
        );
        else if (this.request.length == 1) return this.request[0];
        else return Arrays.stream(this.request)
                    .filter(ticket -> ticket.getDepartureDate().compareTo(date) > 0)
                    .min(Comparator
                            .comparingLong(FlightTicket::getPrice)
                            .thenComparing(FlightTicket::getDepartureDate)
                            .thenComparingLong(FlightTicket::getDuration)
                    ).orElseThrow(NoSuchElementException::new);
    }


    @Override
    public String toString() {
        return "Quote{" +
                "success=" + success +
                ", value=" + Arrays.toString(request) +
                '}';
    }
}
