package com.kalita.projects.repos;

import com.kalita.projects.domain.TravelNote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TravelNoteRepo extends CrudRepository<TravelNote, Long> {
    List<TravelNote> findByCountryDestination(String country);
}
