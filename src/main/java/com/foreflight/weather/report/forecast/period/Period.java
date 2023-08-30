package com.foreflight.weather.report.forecast.period;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Period {

    private String dateStart;
    private String dateEnd;
}
