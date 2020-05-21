package com.kalita.projects.service;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.repos.TravelNoteRepo;
import org.springframework.stereotype.Service;

@Service
public class TravelNoteService {

    private final TravelNoteRepo noteRepo;


    public TravelNoteService(TravelNoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public Iterable<TravelNote> showFilteringNotes(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return noteRepo.findByCountryDestination(filter);
        } else {
            return noteRepo.findAll();
        }
    }

    public void save(TravelNote travelNote) {
        noteRepo.save(travelNote);
    }

    public Iterable<TravelNote> findAll() {
        return noteRepo.findAll();
    }

    public void delete(TravelNote note) {
        noteRepo.delete(note);
    }
}
