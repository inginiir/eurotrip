package com.kalita.projects.controllers;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.User;
import com.kalita.projects.service.CityService;
import com.kalita.projects.service.CountryService;
import com.kalita.projects.service.TravelNoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class TravelNoteController {

    @Value("${upload.path}")
    private String uploadPath;

    private final TravelNoteService travelNoteService;
    private final CityService cityService;
    private final CountryService countryService;

    public TravelNoteController(TravelNoteService travelNoteService, CityService cityService, CountryService countryService) {
        this.travelNoteService = travelNoteService;
        this.cityService = cityService;
        this.countryService = countryService;
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
                       Model model,
                       HttpServletRequest httpServletRequest
    ) {
        Iterable<TravelNote> travelNotes = travelNoteService.showFilteringNotes(filter);
        String errorMessageTicket = (String) httpServletRequest.getSession().getAttribute("ticketError");
        String errorMessageCity = (String) httpServletRequest.getSession().getAttribute("cityError");
        model.addAttribute("ticketError", errorMessageTicket);
        model.addAttribute("cityError", errorMessageCity);
        model.addAttribute("filter", filter);
        model.addAttribute("travelNotes", travelNotes);
        model.addAttribute("cities", cityService.findAll());
        return "main";
    }

    @PostMapping("/main")
    public String addTravelNote(@AuthenticationPrincipal User user,
                                @Valid TravelNote travelNote,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date departureDate,
                                @RequestParam("originCity") String originCity,
                                @RequestParam(required = false, name = "city") String[] cities,
                                HttpServletRequest httpServletRequest
    ) throws IOException {

        travelNote.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("travelNote", travelNote);
            model.addAttribute("cities", cityService.findAll());
            model.addAttribute("travelNotes", travelNoteService.findAll());
            return "main";
        } else if (originCity.isEmpty() || cities.length == 0) {
            model.addAttribute("cityError", "Please enter at least 2 cities: yours and which you would like to visit in the field below");
            model.addAttribute("cities", cityService.findAll());
            model.addAttribute("travelNotes", travelNoteService.findAll());
            return "main";
        } else {
            if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
                File uploadFolder = new File(uploadPath);
                boolean mkdir = true;
                if (!uploadFolder.exists()) {
                    mkdir = uploadFolder.mkdir();
                }
                if (mkdir) {
                    String uuidFile = UUID.randomUUID().toString();
                    String resultFileName = uuidFile + "." + file.getOriginalFilename();
                    file.transferTo(new File(uploadPath + "/" + resultFileName));
                    travelNote.setFilename(resultFileName);
                }
            }
            httpServletRequest.getSession().setAttribute("travelNote", travelNote);
            model.addAttribute("travelNote", null);
            //travelNoteService.save(travelNote);
        }
        model.addAttribute("travelNotes", travelNoteService.findAll());
        httpServletRequest.getSession().setAttribute("originCity", originCity);
        httpServletRequest.getSession().setAttribute("departureDate", departureDate);
        httpServletRequest.getSession().setAttribute("cities", cities);
        return "redirect:/ticket/create/";
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
                           Model model
    ) {
        model.addAttribute("note", note);
        return "noteEdit";
    }

    @PostMapping("/main/editNote/")
    public String travelNoteSave(@RequestParam("noteId") TravelNote travelNote,
                                 @RequestParam String countryDestination,
                                 //@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date travelDate,
                                 @RequestParam String note
                                 //@RequestParam(value = "isVisited", required = false, defaultValue = "false") Boolean isVisited
    ) {
        travelNote.setNameNote(countryDestination);
        travelNote.setNote(note);
        travelNoteService.save(travelNote);
        return "redirect:/main";
    }

}
