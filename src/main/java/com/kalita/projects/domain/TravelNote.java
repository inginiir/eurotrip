package com.kalita.projects.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TravelNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String countryDestination;
    private Date travelDate;
    private String note;
    private Boolean isVisited;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public TravelNote(String countryDestination, Date travelDate, String note, boolean isVisited, User user) {
        this.countryDestination = countryDestination;
        this.travelDate = travelDate;
        this.note = note;
        this.isVisited = isVisited;
        this.author = user;
    }

    public TravelNote() {
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCountryDestination() {
        return countryDestination;
    }

    public void setCountryDestination(String countryDestination) {
        this.countryDestination = countryDestination;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
