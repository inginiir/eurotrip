package com.kalita.projects.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class TravelNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please fill the field")
    @Length(max = 2048, message = "Note too long (more 2kB)")
    private String countryDestination;
    //@NotBlank(message = "Please select the travel date")
    private Date travelDate;
    @NotBlank(message = "Please fill the travel note")
    @Length(max = 2048, message = "Note too long (more 2kB)")
    private String note;
    private Boolean isVisited;
    private String filename;

    public TravelNote() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVisited() {
        return isVisited;
    }

    public void setVisited(Boolean visited) {
        isVisited = visited;
    }

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
