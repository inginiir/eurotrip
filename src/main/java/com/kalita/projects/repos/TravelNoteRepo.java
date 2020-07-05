package com.kalita.projects.repos;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelNoteRepo extends CrudRepository<TravelNote, Long> {

    Page<TravelNote> findAll(Pageable pageable);

    Page<TravelNote> findByNameNote(String country, Pageable pageable);

    @Query("from TravelNote t where t.author = :author")
    Page<TravelNote> findByUser(Pageable pageable, @Param("author") User author);
}
