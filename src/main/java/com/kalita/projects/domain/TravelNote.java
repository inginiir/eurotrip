package com.kalita.projects.domain;

import com.kalita.projects.domain.dto.FlightTicket;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TravelNote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Please fill the field")
    @Length(max = 2048, message = "Note too long (more 2kB)")
    private String nameNote;
    @NotBlank(message = "Please fill the field")
    @Length(max = 2048, message = "Note too long (more 2kB)")
    private String note;
    private String filename;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    @OneToMany(mappedBy = "travelNote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FlightTicket> tickets = new HashSet<>();

    public TravelNote() {
    }

    public TravelNote(String nameNote, String note, User user) {
        this.nameNote = nameNote;
        this.note = note;
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

    public String getNameNote() {
        return nameNote;
    }

    public void setNameNote(String nameNote) {
        this.nameNote = nameNote;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FlightTicket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<FlightTicket> tickets) {
        this.tickets = tickets;
    }
}
