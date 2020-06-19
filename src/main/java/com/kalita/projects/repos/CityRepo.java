package com.kalita.projects.repos;

import com.kalita.projects.domain.dto.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends CrudRepository<City, Long> {

    City findByCode(String code);

    City findByName(String name);

    City findByNameAndCountryCode(String name, String countryCode);
}
