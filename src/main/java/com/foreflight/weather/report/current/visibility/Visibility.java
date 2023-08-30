package com.foreflight.weather.report.current.visibility;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Visibility{
    private Double distanceSm;
}
