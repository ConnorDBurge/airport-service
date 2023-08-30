package com.foreflight.weather.report.forecast.period;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Period {

    private String dateStart;
    private String dateEnd;
}
