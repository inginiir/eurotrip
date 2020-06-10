package com.kalita.projects.repos;

import com.kalita.projects.domain.dto.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepo extends CrudRepository<Country, Long> {
}
