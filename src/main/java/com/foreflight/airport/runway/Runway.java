package com.foreflight.airport.runway;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Runway implements Comparable<Runway> {
    private String ident;
    private String name;
    private String recipName;
    private Integer magneticHeading;
    private Integer recipMagneticHeading;
    @Setter private Double crossWind;
    @Setter private Double headWind;
    @Setter private boolean bestRunway;

    @Override
    public int compareTo(Runway other) {
        return this.ident.compareTo(other.ident);
    }
}
