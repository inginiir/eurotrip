package com.kalita.projects.controllers;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.User;
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

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class GreetingController {

    @Value("${upload.path}")
    private String uploadPath;

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
        Iterable<TravelNote> travelNotes = travelNoteService.showFilteringNotes(filter);
        model.addAttribute("filter", filter);
        model.addAttribute("travelNotes", travelNotes);
        return "main";
    }

    @PostMapping("/main")
    public String addTravelNote(@AuthenticationPrincipal User user,
                                @Valid TravelNote travelNote,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam("file") MultipartFile file) throws IOException {
        if (travelNote.getVisited() == null) {
            travelNote.setVisited(false);
        }
        travelNote.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("travelNote", travelNote);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadFolder = new File(uploadPath);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                travelNote.setFilename(resultFileName);
            }
            model.addAttribute("travelNote", null);
            travelNoteService.save(travelNote);
        }
        Iterable<TravelNote> travelNotes = travelNoteService.findAll();
        model.addAttribute("travelNotes", travelNotes);
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
                                 @RequestParam(value = "isVisited", required = false, defaultValue = "false") Boolean isVisited) {

        travelNote.setCountryDestination(countryDestination);
        travelNote.setTravelDate(travelDate);
        travelNote.setNote(note);
        travelNote.setVisited(isVisited);
        travelNoteService.save(travelNote);
        return "redirect:/main";
    }

}
