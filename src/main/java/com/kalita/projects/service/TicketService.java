package com.kalita.projects.service;

import com.kalita.projects.domain.dto.Ticket;
import com.kalita.projects.repos.TicketRepo;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private TicketRepo ticketRepo;

    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public Iterable<Ticket> findAll() {
        return ticketRepo.findAll();
    }

    public void save(Ticket ticket) {
        ticketRepo.save(ticket);
    }
}
