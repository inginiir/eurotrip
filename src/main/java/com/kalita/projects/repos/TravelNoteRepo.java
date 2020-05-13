package com.kalita.projects.repos;

import com.kalita.projects.domain.TravelNote;
import org.springframework.data.repository.CrudRepository;

public interface TravelNoteRepo extends CrudRepository<TravelNote, Long> {
}
