package com.kalita.projects.controllers;

import com.kalita.projects.SpringApp;
import com.kalita.projects.domain.dto.City;
import com.kalita.projects.domain.dto.Country;
import com.kalita.projects.domain.dto.Quote;
import com.kalita.projects.domain.dto.Ticket;
import com.kalita.projects.service.CityService;
import com.kalita.projects.service.CountryService;
import com.kalita.projects.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;
    private RestTemplate restTemplate;
    private CityService cityService;
    private CountryService countryService;
    private static final Logger log = LoggerFactory.getLogger(SpringApp.class);

    @Value("${aviasales.token}")
    private String token;


    public TicketController(TicketService ticketService, CityService cityService, CountryService countryService) {
        this.ticketService = ticketService;
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping
    public String showTickets(Model model) {
        Iterable<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        Quote quote = getDataFromAviasales("MOW", "PAR", "2020-07");
//        ticketService.save(quote.getTicket().get("PAR").get("2"));
        saveCountriesToDatabase();
        saveCitiesToDatabase();
        return "ticket";
    }

    private Quote getDataFromAviasales(String originCountry, String destinationCountry, String departureDate) {
        return restTemplate.getForObject(
                "http://api.travelpayouts.com/v1/prices/cheap?" +
                        "origin=" + originCountry +
                        "&destination=" + destinationCountry +
                        "&depart_date=" + departureDate +
                        "&token=" + token, Quote.class);
    }

    private void saveCitiesToDatabase() {
        City[] cities = restTemplate.getForObject("http://api.travelpayouts.com/data/ru/cities.json",
                City[].class);
        if (cities != null) {
            Iterable<City> cities1 = Arrays.stream(cities)
                    .filter(city -> city.getName() != null && city.getCode() != null && city.getCountryCode() != null)
                    .collect(Collectors.toSet());
            cityService.saveAll(cities1);
        }
    }
    private void saveCountriesToDatabase() {
        Country[] countries = restTemplate.getForObject("http://api.travelpayouts.com/data/ru/countries.json",
                Country[].class);
        if (countries != null) {
            Iterable<Country> countries1 = Arrays.stream(countries)
                    .filter(country -> country.getName() != null && country.getCode() != null)
                    .collect(Collectors.toSet());
            countryService.saveAll(countries1);
        }
    }
}
