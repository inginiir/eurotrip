package com.kalita.projects.service;

import com.kalita.projects.domain.dto.Country;
import com.kalita.projects.repos.CountryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private CountryRepo countryRepo;

    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    public void saveAll(Iterable<Country> countries1) {
        countryRepo.saveAll(countries1);
    }

    public Country findByCode(String code) {
        return countryRepo.findByCode(code);
    }

    public Iterable<Country> findAll() {
        return countryRepo.findAll();
    }
}
