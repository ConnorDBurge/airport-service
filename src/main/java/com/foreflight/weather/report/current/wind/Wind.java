package com.foreflight.weather.report.current.wind;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Wind {
    private Double speedKts;
    private Integer direction;
    private Integer from;
    private Boolean variable;
}
