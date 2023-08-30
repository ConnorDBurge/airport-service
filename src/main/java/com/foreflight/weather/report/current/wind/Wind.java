package com.foreflight.weather.report.current.wind;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wind {
    private Double speedKts;
    private Integer direction;
    private Integer from;
    private Boolean variable;
}
