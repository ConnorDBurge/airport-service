package com.foreflight.airport.runway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Runway {
    private String ident;
    private String name;
    private String recipName;
}
