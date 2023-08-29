package com.foreflight.airport;

import com.foreflight.airport.runway.Runway;
import com.foreflight.weather.Weather;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airport {

    @Id
    @GeneratedValue
    private UUID guid;
    private String icao;
    private String name;
    private Float latitude;
    private Float longitude;

    @OneToMany
    @JoinColumn(name = "airport_guid", referencedColumnName = "guid")
    private List<Runway> runways;

    @OneToOne
    private Weather weather;
}
