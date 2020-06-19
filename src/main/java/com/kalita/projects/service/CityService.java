package com.kalita.projects.service;

import com.kalita.projects.domain.dto.City;
import com.kalita.projects.repos.CityRepo;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private CityRepo cityRepo;

    public CityService(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    public void saveAll(Iterable<City> cities) {
        cityRepo.saveAll(cities);
    }

    public City findByCityCode(String code) {
        return cityRepo.findByCode(code);
    }

    public City findByName(String name) {
        return cityRepo.findByName(name);
    }

    public Iterable<City> findAll() {
        return cityRepo.findAll();
    }

    public City findByNameAndCountryCode(String name, String countryCode) {
        return cityRepo.findByNameAndCountryCode(name, countryCode);
    }
}
