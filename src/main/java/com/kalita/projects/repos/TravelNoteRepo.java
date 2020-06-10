package com.kalita.projects.repos;

import com.kalita.projects.domain.TravelNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelNoteRepo extends CrudRepository<TravelNote, Long> {
    List<TravelNote> findByNameNote(String country);
}
