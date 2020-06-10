package com.kalita.projects.repos;

import com.kalita.projects.domain.dto.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends CrudRepository<Ticket, Long> {
}
