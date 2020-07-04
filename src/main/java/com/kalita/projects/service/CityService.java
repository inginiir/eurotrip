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

    void saveAll(Iterable<City> cities) {
        cityRepo.saveAll(cities);
    }

    public Iterable<City> findAll() {
        return cityRepo.findAll();
    }

    public Iterable<City> findJoinedTable() {
        return cityRepo.findJoinTable();
    }

    City findByNameAndCountryCode(String name, String countryCode) {
        return cityRepo.findByNameAndCountryCode(name, countryCode);
    }

    public City findByCityCode(String codeCity) {
        return cityRepo.findByCode(codeCity);
    }
}
