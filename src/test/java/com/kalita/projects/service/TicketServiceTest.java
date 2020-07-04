package com.kalita.projects.service;

import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.User;
import com.kalita.projects.domain.dto.City;
import com.kalita.projects.domain.dto.JsonFromAviaSales;
import com.kalita.projects.repos.TicketRepo;
import com.kalita.projects.service.exceptions.CityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

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
                new TravelNote()
                )
        );
    }

}