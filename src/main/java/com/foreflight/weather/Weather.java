package com.foreflight.weather;

import com.foreflight.weather.report.Report;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    @Setter
    private String ident;
    private Report report;
}
