package com.foreflight.airport.runway;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Runway {

    @Id
    @GeneratedValue
    private UUID guid;
    private String ident;
    private String name;
    private String recipName;
}
