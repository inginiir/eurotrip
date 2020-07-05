package com.kalita.projects.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kalita.projects.domain.TravelNote;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightTicket implements Comparable<FlightTicket> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonAlias("value")
    private Long price;
    @JsonAlias("origin")
    private String originCityCode;
    @JsonAlias("number_of_changes")
    private Integer numberOfChanges;
    private Long duration;
    private Long distance;
    @JsonAlias("destination")
    private String destinationCityCode;
    @JsonAlias("depart_date")
    private Date departureDate;
    @JsonAlias("actual")
    private Boolean isActual;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "travelNote_id")
    private TravelNote travelNote;
    @OneToOne
    private City cityOfDepart;
    @OneToOne
    private Country countryOfDepart;
    @OneToOne
    private City cityArrive;
    @OneToOne
    private Country countryArrive;

    public FlightTicket() {
    }

    public FlightTicket(
            Long price,
            String originCityCode,
            Integer numberOfChanges,
            Long duration,
            Long distance,
            String destinationCityCode,
            Date departureDate,
            Boolean isActual,
            TravelNote travelNote
    ) {
        this.price = price;
        this.originCityCode = originCityCode;
        this.numberOfChanges = numberOfChanges;
        this.duration = duration;
        this.distance = distance;
        this.destinationCityCode = destinationCityCode;
        this.departureDate = departureDate;
        this.isActual = isActual;
        this.travelNote = travelNote;
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

    public String getOriginCityCode() {
        return originCityCode;
    }

    public void setOriginCityCode(String originCityCode) {
        this.originCityCode = originCityCode;
    }

    public Integer getNumberOfChanges() {
        return numberOfChanges;
    }

    public void setNumberOfChanges(Integer numberOfChanges) {
        this.numberOfChanges = numberOfChanges;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public String getDestinationCityCode() {
        return destinationCityCode;
    }

    public void setDestinationCityCode(String destinationCityCode) {
        this.destinationCityCode = destinationCityCode;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Boolean getActual() {
        return isActual;
    }

    public void setActual(Boolean actual) {
        isActual = actual;
    }

    public TravelNote getTravelNote() {
        return travelNote;
    }

    public void setTravelNote(TravelNote travelNote) {
        this.travelNote = travelNote;
    }

    public City getCityOfDepart() {
        return cityOfDepart;
    }

    public void setCityOfDepart(City cityOfDepart) {
        this.cityOfDepart = cityOfDepart;
    }

    public Country getCountryOfDepart() {
        return countryOfDepart;
    }

    public void setCountryOfDepart(Country countryOfDepart) {
        this.countryOfDepart = countryOfDepart;
    }

    public City getCityArrive() {
        return cityArrive;
    }

    public void setCityArrive(City cityArrive) {
        this.cityArrive = cityArrive;
    }

    public Country getCountryArrive() {
        return countryArrive;
    }

    public void setCountryArrive(Country countryArrive) {
        this.countryArrive = countryArrive;
    }

    @Override
    public String toString() {
        return "FlightTicket{" +
                "price=" + price +
                ", originCityCode='" + originCityCode + '\'' +
                ", numberOfChanges=" + numberOfChanges +
                ", duration=" + duration +
                ", distance=" + distance +
                ", destinationCityCode='" + destinationCityCode + '\'' +
                ", departureDate=" + departureDate +
                ", isActual=" + isActual +
                '}';
    }

    @Override
    public int compareTo(FlightTicket o) {
        return this.getDepartureDate().compareTo(o.getDepartureDate());
    }
}
