package com.kalita.projects.repos;

import com.kalita.projects.domain.dto.City;
import com.kalita.projects.domain.dto.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends CrudRepository<City, Long> {

    City findByNameAndCountryCode(String name, String countryCode);

    City findByCode(String code);

    @Query("SELECT new City(ci.name, ci.code, c.name) FROM City ci " +
            "JOIN Country c ON ci.countryCode = c.code "
    )
    Iterable<City> findJoinTable();
}
