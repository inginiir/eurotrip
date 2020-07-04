package com.kalita.projects.service;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.User;
import com.kalita.projects.repos.TravelNoteRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TravelNoteService {

    private final TravelNoteRepo noteRepo;


    public TravelNoteService(TravelNoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public Page<TravelNote> showFilteringNotes(String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            return noteRepo.findByNameNote(filter, pageable);
        } else {
            return noteRepo.findAll(pageable);
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

    public Page<TravelNote> notesListForUser(Pageable pageable, User author) {
        return noteRepo.findByUser(pageable, author);
    }

    public Page<TravelNote> findAll(Pageable pageable) {
        return noteRepo.findAll(pageable);
    }
}
