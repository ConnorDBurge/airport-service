package com.foreflight.airport.runway;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Runway {
    private String ident;
    private String name;
    private String recipName;
    private Integer magneticHeading;
    private Integer recipMagneticHeading;
    @Setter private Double crossWind;
    @Setter private Double headWind;
}
