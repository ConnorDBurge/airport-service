package com.foreflight.airport;

import com.foreflight.TestConfig;
import com.foreflight.airport.runway.Runway;
import com.foreflight.external.AirportAPI;
import com.foreflight.external.WeatherAPI;
import com.foreflight.weather.Weather;
import com.foreflight.weather.report.Report;
import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.current.wind.Wind;
import com.foreflight.weather.report.forecast.Forecast;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Import(TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirportControllerValidTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired private AirportAPI airportAPI;
    @Autowired private WeatherAPI weatherAPI;
    private final String AIRPORT_URI = "/v1/airports/";

    @Test
    void canGetAirport() {
        String ident = "KFFC";

        Weather mockedWeather = Weather.builder()
                .ident(ident)
                .report(Report.builder()
                        .current(Current.builder()
                                .wind(Wind.builder()
                                        .from(130)
                                        .speedKts(10.0)
                                        .variable(false)
                                        .build())
                                .build())
                        .forecast(Forecast.builder()
                                .ident("KATL")
                                .build())
                        .build())
                .build();

        Airport mockedAirport = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Falcon Field")
                .magneticVariation(5)
                .runways(List.of(Runway.builder()
                        .ident("13-31")
                        .magneticHeading(130)
                        .recipMagneticHeading(310)
                        .name("13")
                        .recipName("31")
                        .build()))
                .weather(mockedWeather)
                .build();

        ResponseEntity<Airport> airportResponse = ResponseEntity.ok(mockedAirport);
        ResponseEntity<Weather> weatherResponse = ResponseEntity.ok(mockedWeather);

        when(airportAPI.findAirport(ident)).thenReturn(airportResponse);
        when(weatherAPI.findWeather(ident)).thenReturn(weatherResponse);

        List<AirportDTO> airports = webTestClient.get()
                .uri(AIRPORT_URI + ident)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<AirportDTO>() {
                })
                .returnResult()
                .getResponseBody();

        verify(airportAPI, times(1)).findAirport(ident);
        verify(weatherAPI, times(1)).findWeather(ident);

        assertThat(airports).hasSize(1);
        AirportDTO returnedAirport = airports.get(0);
        assertThat(returnedAirport.getIdent()).isEqualTo(ident);
        assertThat(returnedAirport.getName()).isEqualTo("Atlanta Regional Falcon Field");
    }

    @Test
    void canGetMultipleAirports() {
        String idents = "KFFC,KATL";

        Weather mockedWeather1 = Weather.builder()
                .ident("KFFC")
                .report(Report.builder()
                        .current(Current.builder()
                                .wind(Wind.builder()
                                        .from(130)
                                        .speedKts(10.0)
                                        .variable(false)
                                        .build())
                                .build())
                        .forecast(Forecast.builder()
                                .ident("KATL")
                                .build())
                        .build())
                .build();

        Airport mockedAirport1 = Airport.builder()
                .icao("KFFC")
                .name("Atlanta Regional Falcon Field")
                .magneticVariation(5)
                .runways(List.of(Runway.builder()
                        .ident("13-31")
                        .magneticHeading(130)
                        .recipMagneticHeading(310)
                        .build()))
                .weather(mockedWeather1)
                .build();

        Weather mockedWeather2 = Weather.builder()
                .ident("KATL")
                .report(Report.builder()
                        .current(Current.builder()
                                .wind(Wind.builder()
                                        .from(130)
                                        .speedKts(10.0)
                                        .variable(false)
                                        .build())
                                .build())
                        .forecast(Forecast.builder()
                                .ident("KATL")
                                .build())
                        .build())
                .build();

        Airport mockedAirport2 = Airport.builder()
                .icao("KATL")
                .name("Hartsfield - Jackson Atlanta International")
                .magneticVariation(5)
                .runways(List.of(Runway.builder()
                        .ident("13-31")
                        .magneticHeading(130)
                        .recipMagneticHeading(310)
                        .build()))
                .weather(mockedWeather2)
                .build();

        ResponseEntity<Airport> airportResponse1 = ResponseEntity.ok(mockedAirport1);
        ResponseEntity<Airport> airportResponse2 = ResponseEntity.ok(mockedAirport2);
        ResponseEntity<Weather> weatherResponse1 = ResponseEntity.ok(mockedWeather1);
        ResponseEntity<Weather> weatherResponse2 = ResponseEntity.ok(mockedWeather2);

        when(airportAPI.findAirport("KFFC")).thenReturn(airportResponse1);
        when(airportAPI.findAirport("KATL")).thenReturn(airportResponse2);
        when(weatherAPI.findWeather("KFFC")).thenReturn(weatherResponse1);
        when(weatherAPI.findWeather("KATL")).thenReturn(weatherResponse2);

        List<AirportDTO> airports = webTestClient.get()
                .uri(AIRPORT_URI + idents)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<AirportDTO>() {
                })
                .returnResult()
                .getResponseBody();

        verify(airportAPI, times(2)).findAirport(anyString());
        verify(weatherAPI, times(2)).findWeather(anyString());

        assertThat(airports).hasSize(2);
        AirportDTO kffc = airports.get(0);
        assertThat(kffc.getIdent()).isEqualTo("KFFC");
        assertThat(kffc.getName()).isEqualTo("Atlanta Regional Falcon Field");
        AirportDTO katl = airports.get(1);
        assertThat(katl.getIdent()).isEqualTo("KATL");
        assertThat(katl.getName()).isEqualTo("Hartsfield - Jackson Atlanta International");
    }
}
