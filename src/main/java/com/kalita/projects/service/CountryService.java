package com.kalita.projects.service;

import com.kalita.projects.domain.dto.Country;
import com.kalita.projects.repos.CountryRepo;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private CountryRepo countryRepo;

    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    public void saveAll(Iterable<Country> countries1) {
        countryRepo.saveAll(countries1);
    }
}
