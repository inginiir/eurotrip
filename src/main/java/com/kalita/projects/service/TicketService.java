package com.kalita.projects.service;

import com.kalita.projects.SpringApp;
import com.kalita.projects.domain.TravelNote;
import com.kalita.projects.domain.dto.City;
import com.kalita.projects.domain.dto.Country;
import com.kalita.projects.domain.dto.FlightTicket;
import com.kalita.projects.domain.dto.JsonFromAviaSales;
import com.kalita.projects.repos.TicketRepo;
import com.kalita.projects.service.exceptions.CityNotFoundException;
import com.kalita.projects.service.handlers.RestTemplateResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private TicketRepo ticketRepo;
    private RestTemplate restTemplate;
    private CityService cityService;
    private CountryService countryService;

    private static final Logger log = LoggerFactory.getLogger(SpringApp.class);

    @Value("${aviasales.token}")
    private String token;

    public TicketService(TicketRepo ticketRepo, CityService cityService, CountryService countryService) {
        this.ticketRepo = ticketRepo;
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public Iterable<FlightTicket> findByTravelNoteId(Long id) {
        return ticketRepo.findByTravelNoteId(id);
    }

    public void createRoutesBetweenCities(
            String originCity,
            Date departureDate,
            String[] cities,
            TravelNote travelNote
    ) throws CityNotFoundException, NoSuchElementException {
        if (departureDate == null) {
            departureDate = new Date();
        }
        String dateOfFlight = getStringDate(departureDate);
        List<String> cityCodes = new ArrayList<>();
        String codeOriginCity = getCityCode(originCity);
        cityCodes.add(codeOriginCity);
        for (String city : cities) {
            String codeCity = getCityCode(city);
            cityCodes.add(codeCity);
        }
        List<FlightTicket> tickets = new ArrayList<>();
        List<FlightTicket> resultFlightTicketsList = new ArrayList<>();
        Date currentDate = departureDate;
        String codeCityOfDepart = codeOriginCity;
        while (cityCodes.size() > 1) {
            tickets.clear();
            for (String codeCity : cityCodes) {
                if (!codeCity.equals(codeCityOfDepart)) {
                    JsonFromAviaSales monthMatrix = getMonthMatrix(codeCityOfDepart, codeCity, dateOfFlight);
                    currentDate = addDaysToDate(currentDate, 0);
                    FlightTicket flightTicket = monthMatrix.getCheapestFlightTicket(travelNote, currentDate);
                    if (flightTicket != null) {
                        flightTicket.setTravelNote(travelNote);
                        currentDate = flightTicket.getDepartureDate();
                        tickets.add(flightTicket);
                    }
                }
            }
            FlightTicket ticket = getCheapTicket(tickets, travelNote);
            resultFlightTicketsList.add(ticket);
            cityCodes.remove(codeCityOfDepart);
            codeCityOfDepart = ticket.getDestinationCityCode();
        }
        JsonFromAviaSales monthMatrixLast = getMonthMatrix(codeCityOfDepart, codeOriginCity, dateOfFlight);
        FlightTicket lastFlightTicket = monthMatrixLast.getCheapestFlightTicket(travelNote, currentDate);
        lastFlightTicket.setTravelNote(travelNote);
        resultFlightTicketsList.add(lastFlightTicket);
        ticketRepo.saveAll(resultFlightTicketsList);
    }

    private Date addDaysToDate(Date currentDate, int numberDays) {
        String currentDateString = getStringDate(currentDate);
        LocalDate localDate = LocalDate.parse(currentDateString).plusDays(numberDays);
        long date = localDate.toEpochDay() * 24 * 60 * 60 * 1000;
        currentDate = new Date(date);
        return currentDate;
    }

    private String getStringDate(Date departureDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(departureDate);
    }

    private String getCityCode(String cityNameAndCountryCode) throws CityNotFoundException {
        String[] nameAndCountryCode = cityNameAndCountryCode.split("/");
        String cityCode = "";
        if (nameAndCountryCode.length == 2) {
            String cityName = nameAndCountryCode[0];
            String countryCode = nameAndCountryCode[1];
            City currentCity = cityService.findByNameAndCountryCode(cityName, countryCode);
            if (currentCity == null) {
                throw new CityNotFoundException();
            } else {
                cityCode = currentCity.getCode();
            }
        }
        return cityCode;
    }

    private JsonFromAviaSales getMonthMatrix(
            String originCountry,
            String destinationCountry,
            String departureDate
    ) throws CityNotFoundException {
        String uri = "http://api.travelpayouts.com/v2/prices/month-matrix?" +
                "currency=rub" +
                "&origin=" + originCountry +
                "&destination=" + destinationCountry +
                "&month=" + departureDate +
                "&token=" + token;

        return restTemplate.getForObject(uri, JsonFromAviaSales.class);
    }

    private FlightTicket getCheapTicket(List<FlightTicket> flightTickets, TravelNote travelNote) {
        if (flightTickets.size() == 0)
            return new FlightTicket(
                    0L,
                    "Sorry, flight ticket not found",
                    0,
                    0L,
                    0L,
                    "-",
                    new Date(),
                    false,
                    travelNote
            );
        else if (flightTickets.size() == 1) {
            return flightTickets.get(0);
        } else {
            return Collections.min(flightTickets, Comparator
                    .comparingLong(FlightTicket::getPrice)
                    .thenComparingLong(FlightTicket::getDuration)
            );
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
}
