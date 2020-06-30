package com.kalita.projects.controllers;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.dto.FlightTicket;
import com.kalita.projects.service.CityService;
import com.kalita.projects.service.TicketService;
import com.kalita.projects.service.TravelNoteService;
import com.kalita.projects.service.exceptions.CityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;
    private TravelNoteService travelNoteService;
    private CityService cityService;

    public TicketController(TicketService ticketService, TravelNoteService travelNoteService, CityService cityService) {
        this.ticketService = ticketService;
        this.travelNoteService = travelNoteService;
        this.cityService = cityService;
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
            HttpServletRequest httpServletRequest,
            Model model
    ) {
        String originCity = (String) httpServletRequest.getSession().getAttribute("originCity");
        Date departureDate = (Date) httpServletRequest.getSession().getAttribute("departureDate");
        String[] cities = (String[]) httpServletRequest.getSession().getAttribute("cities");
        TravelNote travelNote = (TravelNote) httpServletRequest.getSession().getAttribute("travelNote");
        try {
            travelNoteService.save(travelNote);
            ticketService.createRoutesBetweenCities(originCity, departureDate, cities, travelNote);
        } catch (CityNotFoundException e) {
            travelNoteService.delete(travelNote);
            model.addAttribute("cityError", "Sorry, tickets between this cities not found, please try another cities");
            model.addAttribute("cities", cityService.findAll());
            model.addAttribute("travelNotes", travelNoteService.findAll());
            httpServletRequest.getSession().setAttribute("cityError", "Sorry, city not found");
            return "redirect:/main";
        } catch (NoSuchElementException e) {
            travelNoteService.delete(travelNote);
            model.addAttribute("cities", cityService.findAll());
            model.addAttribute("ticketError", "Sorry, ticket not found, please try another city or date");
            httpServletRequest.getSession().setAttribute("ticketError", "Sorry, ticket not found, please try another city or date");
            model.addAttribute("travelNotes", travelNoteService.findAll());
            return "redirect:/main";
        }
        return "redirect:/main";
    }


}
