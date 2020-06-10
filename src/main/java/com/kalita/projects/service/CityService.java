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
}
