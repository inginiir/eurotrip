package com.kalita.projects.repos;

import com.kalita.projects.domain.dto.FlightTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends CrudRepository<FlightTicket, Long> {

    Iterable<FlightTicket> findByTravelNoteId(Long id);
}
