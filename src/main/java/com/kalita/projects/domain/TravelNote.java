package com.kalita.projects.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class TravelNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please fill the country")
    @Length(max = 2048, message = "Note too long (more 2kB)")
    private String countryDestination;
    private Date travelDate;
    @NotBlank(message = "Please fill the travel note")
    @Length(max = 2048, message = "Note too long (more 2kB)")
    private String note;
    private Boolean isVisited;
    private String filename;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public TravelNote() {
    }

    public TravelNote(String countryDestination, Date travelDate, String note, boolean isVisited, User user) {
        this.countryDestination = countryDestination;
        this.travelDate = travelDate;
        this.note = note;
        this.isVisited = isVisited;
        this.author = user;
    }

    public Boolean getVisited() {
        return isVisited;
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

    public void setVisited(Boolean visited) {
        isVisited = visited;
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
