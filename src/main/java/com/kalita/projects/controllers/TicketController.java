package com.kalita.projects.controllers;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.dto.FlightTicket;
import com.kalita.projects.service.TicketService;
import com.kalita.projects.service.exceptions.CityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{travelNote}")
    public String showTickets(
            Model model,
            @PathVariable TravelNote travelNote
    ) {
        Iterable<FlightTicket> tickets = ticketService.findByTravelNoteId(travelNote.getId());
        model.addAttribute("tickets", tickets);
        return "ticket";
    }

    @GetMapping("/create")
    public String createTickets(
            HttpServletRequest httpServletRequest
    ) {
        String originCity = (String) httpServletRequest.getSession().getAttribute("originCity");
        Date departureDate = (Date) httpServletRequest.getSession().getAttribute("departureDate");
        String[] cities = (String[]) httpServletRequest.getSession().getAttribute("cities");
        TravelNote travelNote = (TravelNote) httpServletRequest.getSession().getAttribute("travelNote");
        try {
            ticketService.createRoutesBetweenCities(originCity, departureDate, cities, travelNote);
        } catch (CityNotFoundException e) {

        }
        return "redirect:/main";
    }


}
