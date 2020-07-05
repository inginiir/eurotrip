package com.kalita.projects.service;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.repos.TicketRepo;
import com.kalita.projects.service.exceptions.CityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

class TicketServiceTest {

    private TicketRepo ticketRepo = mock(TicketRepo.class);
    private CountryService countryService = mock(CountryService.class);
    private CityService cityService = mock(CityService.class);
    private TicketService ticketService = new TicketService(ticketRepo, cityService, countryService);

    @Test
    void createRoutesBetweenCitiesThrowCityNotFoundExceptionFromDB() {
        Mockito.doReturn(null)
                .when(cityService).findByNameAndCountryCode("FakeCity", "c");
        String[] cities = {"city1/c1", "city2/c2"};
        assertThrows(CityNotFoundException.class, () -> ticketService.createRoutesBetweenCities(
                "FakeCity/c",
                new Date(),
                cities,
                new TravelNote(),
                0)
        );
    }

}