package com.kalita.projects.repos;

import com.kalita.projects.domain.dto.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CountryRepo extends CrudRepository<Country, Long> {

    @Query("SELECT new Country(c.id, c.code, c.name) FROM Country c " +
            "JOIN City ci ON c.code=ci.countryCode " +
            "WHERE ci.code = :code"
    )
    Country findByCode(@Param("code") String code);

}
