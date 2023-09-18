package com.foreflight.util;

import com.foreflight.airport.Airport;
import com.foreflight.airport.runway.Runway;
import com.foreflight.weather.Weather;
import com.foreflight.weather.report.Report;
import com.foreflight.weather.report.current.Current;
import com.foreflight.weather.report.current.wind.Wind;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.foreflight.util.WindCalculator.calculateWindComponents;
import static org.junit.jupiter.api.Assertions.*;

class WindCalculatorTest {

    /**
     * Using a small value EPSILON for floating-point number comparison
     * The EPSILON value is used to deal with floating-point errors that
     * can occur when performing mathematical operations. It ensures that
     * the comparison is within a reasonable tolerance (3 decimal points).
     */
    private static final double EPSILON = 1e-3;

    @Test
    public void testCalculateWindComponents_HeadWind() {
        WindCalculator.WindComponents components = calculateWindComponents(20, 0, 0, 0);
        assertNotNull(components);
        // Expected runway true heading: 0 + 0 = 0 degrees
        // Expected angle difference: 0 - 0 = 0 degrees
        // Expected headwind: 20 * cos(0) = 20 * 1 = 20
        // Expected crosswind: 20 * sin(0) = 20 * 0 = 0
        assertEquals(20, components.getHeadWind(), EPSILON);
        assertEquals(0, components.getCrossWind(), EPSILON);
    }

    @Test
    public void testCalculateWindComponents_CrossWind() {
        WindCalculator.WindComponents components = calculateWindComponents(20, 90, 0, 0);
        assertNotNull(components);
        // Expected runway true heading: 0 + 0 = 0 degrees
        // Expected angle difference: 90 - 0 = 90 degrees
        // Expected headwind: 20 * cos(90) = 20 * 0 = 0
        // Expected crosswind: 20 * sin(90) = 20 * 1 = 20
        assertEquals(0, components.getHeadWind(), EPSILON);
        assertEquals(20, components.getCrossWind(), EPSILON);
    }

    @Test
    public void testCalculateWindComponents_MixedWind() {
        WindCalculator.WindComponents components = calculateWindComponents(20, 45, 0, 0);
        assertNotNull(components);
        // Expected runway true heading: 0 + 0 = 0 degrees
        // Expected angle difference: 45 - 0 = 45 degrees
        // Expected headwind: 20 * cos(45) = 20 * 0.7071 = sqrt(200)
        // Expected crosswind: 20 * sin(45) = 20 * 0.7071 = sqrt(200)
        assertEquals(Math.sqrt(200), components.getHeadWind(), EPSILON);
        assertEquals(Math.sqrt(200), components.getCrossWind(), EPSILON);
    }

    @Test
    public void testCalculateWindComponents_WithMagneticVariation() {
        WindCalculator.WindComponents components = calculateWindComponents(20, 50, 0, 5);
        assertNotNull(components);
        // Expected runway true heading: 0 + 5 = 5 degrees
        // Expected angle difference: 50 - 5 = 45 degrees
        // Expected headwind: 20 * cos(45) = 20 * 0.7071 = 14.142
        // Expected crosswind: 20 * sin(45) = 20 * 0.7071 = 14.142
        assertEquals(14.142, components.getHeadWind(), EPSILON);
        assertEquals(14.142, components.getCrossWind(), EPSILON);
    }

    @Test
    void markBestRunways() {
        Runway runway1 = Runway.builder().headWind(10.0).build();
        Runway runway2 = Runway.builder().headWind(20.0).build();
        Runway runway3 = Runway.builder().headWind(15.0).build();
        Runway runway4 = Runway.builder().headWind(20.0).build();
        Runway runway5 = Runway.builder().headWind(null).build();

        List<Runway> runways = List.of(runway1, runway2, runway3, runway4, runway5);
        WindCalculator.markBestRunways(runways, 20.0);

        assertFalse(runway1.isBestRunway(), "runway1 with 10.0 headwind should not be best");
        assertTrue(runway2.isBestRunway(), "runway2 with 20.0 headwind should be best");
        assertFalse(runway3.isBestRunway(), "runway3 with 15.0 headwind should not be best");
        assertTrue(runway4.isBestRunway(), "runway4 with 20.0 headwind should be best");
        assertFalse(runway5.isBestRunway(), "runway5 with null headwind should not be best");
    }

    @Test
    public void testCalculateWindComponents_WithWeather() {
        Airport airport = Airport.builder()
                .magneticVariation(5)
                .weather(Weather.builder()
                        .report(Report.builder()
                                .current(Current.builder()
                                        .wind(Wind.builder()
                                                .speedKts(20.0)
                                                .from(50)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();
        List<Runway> runways = List.of(
                Runway.builder().ident("36-180").magneticHeading(0).recipMagneticHeading(180).build()
        );

        airport.setRunways(runways);
        calculateWindComponents(airport);

        assertEquals(14.142, runways.get(0).getHeadWind(), EPSILON);
        assertEquals(14.142, runways.get(0).getCrossWind(), EPSILON);
    }

    @Test
    public void testCalculateWindComponents_NoWeather() {
        Airport airport = Airport.builder()
                .weather(Weather.builder()
                        .remarks(new ArrayList<>())
                        .report(Report.builder()
                                .current(null)
                                .build())
                        .build())
                .build();
        List<Runway> runways = new ArrayList<>();
        runways.add(Runway.builder().ident("01-19").build());
        runways.add(Runway.builder().ident("02-20").build());
        airport.setRunways(runways);

        calculateWindComponents(airport);

        assertNull(runways.get(0).getHeadWind());
        assertNull(runways.get(0).getCrossWind());
        assertNull(runways.get(1).getHeadWind());
        assertNull(runways.get(1).getCrossWind());
    }
}
