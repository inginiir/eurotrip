package com.kalita.projects.controllers;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.User;
import com.kalita.projects.service.TravelNoteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
public class GreetingController {

    private final TravelNoteService travelNoteService;

    public GreetingController(TravelNoteService travelNoteService) {
        this.travelNoteService = travelNoteService;
    }

    @GetMapping("/")
    public String first() {
        return "redirect:/greeting";
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model) {
        Iterable<TravelNote> travelNotes =  travelNoteService.showFilteringNotes(filter);
        model.addAttribute("filter", filter);
        model.addAttribute("travelNotes", travelNotes);
        return "main";
    }

    @PostMapping("/main")
    public String addTravelNote(@AuthenticationPrincipal User user,
                                @RequestParam String countryDestination,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date travelDate,
                                @RequestParam String note,
                                @RequestParam(value="isVisited", required = false, defaultValue = "false") boolean isVisited,
                                Map<String, Object> model) {
        TravelNote travelNote = new TravelNote(countryDestination, travelDate, note, isVisited, user);
        travelNoteService.save(travelNote);
        Iterable<TravelNote> travelNotes = travelNoteService.findAll();
        model.put("travelNotes", travelNotes);
        return "main";
    }

    @GetMapping("/main/{note}")
    public String deleteNote(@PathVariable TravelNote note,
                             Model model) {
        travelNoteService.delete(note);
        Iterable<TravelNote> travelNotes = travelNoteService.findAll();
        model.addAttribute("travelNotes", travelNotes);
        return "redirect:/main";
    }

    @GetMapping("/main/editNote/{note}")
    public String editNote(@PathVariable TravelNote note,
                             Model model) {
        model.addAttribute("note", note);
        return "noteEdit";
    }

    @PostMapping("/main/editNote/")
    public String travelNoteSave(@RequestParam("noteId") TravelNote travelNote,
                                 @RequestParam String countryDestination,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date travelDate,
                                 @RequestParam String note,
                                 @RequestParam(value="isVisited", required = false, defaultValue = "false") Boolean isVisited) {

        travelNote.setCountryDestination(countryDestination);
        travelNote.setTravelDate(travelDate);
        travelNote.setNote(note);
        travelNote.setVisited(isVisited);
        travelNoteService.save(travelNote);
        return "redirect:/main";
    }

}
