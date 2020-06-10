package com.kalita.projects.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private Boolean success;
    @JsonAlias("data")
    private Map<String, Map<String, Ticket>> ticket;

    public Quote() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Map<String, Map<String, Ticket>> getTicket() {
        return ticket;
    }

    public void setTicket(Map<String, Map<String, Ticket>> ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "success=" + success +
                ", value=" + ticket +
                '}';
    }
}
