package com.kalita.projects.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kalita.projects.domain.TravelNote;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private String airline;
    @JsonAlias("flight_number")
    private String flightNumber;
    @JsonAlias("departure_at")
    private Date departureDate;
    @OneToOne
    private City city;
    @OneToOne
    private Country country;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "travelNote_id")
    private TravelNote travelNote;

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public TravelNote getTravelNote() {
        return travelNote;
    }

    public void setTravelNote(TravelNote travelNote) {
        this.travelNote = travelNote;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "price=" + price +
                ", airline='" + airline + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", departureDate=" + departureDate +
                ", city=" + city.getName() +
                ", country=" + country.getName() +
                '}';
    }
}
