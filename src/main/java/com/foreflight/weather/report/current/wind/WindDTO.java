package com.foreflight.weather.report.current.wind;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WindDTO {

    private Double speedKts;
    private Integer direction;
    private Integer from;
    private Boolean variable;

    public static WindDTO fromEntity(Wind wind) {
        return WindDTO.builder()
                .speedKts(wind.getSpeedKts())
                .direction(wind.getDirection())
                .from(wind.getFrom())
                .variable(wind.getVariable())
                .build();
    }
}
