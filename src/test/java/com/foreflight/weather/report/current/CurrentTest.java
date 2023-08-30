package com.foreflight.weather.report.current;

import com.foreflight.weather.report.current.cloudlayer.CloudLayer;
import com.foreflight.weather.report.current.visibility.Visibility;
import com.foreflight.weather.report.current.wind.Wind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CurrentTest {

    private Current underTest;
    @Mock private Wind wind;
    @Mock private CloudLayer cloudLayer;
    @Mock private Visibility visibility;

    @BeforeEach
    void setUp() {
        underTest = Current.builder()
                .ident("KFFC")
                .cloudLayers(List.of(cloudLayer))
                .temperature(1.0)
                .relativeHumidity(1.0)
                .visibility(visibility)
                .wind(wind)
                .build();
    }

    @Test
    void getIdent() {
        String actual = underTest.getIdent();
        assertEquals("KFFC", actual);
    }

    @Test
    void getTemperature() {
        Double actual = underTest.getTemperature();
        assertEquals(1.0, actual);
    }

    @Test
    void getRelativeHumidity() {
        Double actual = underTest.getRelativeHumidity();
        assertEquals(1.0, actual);
    }

    @Test
    void getVisibility() {
        Visibility actual = underTest.getVisibility();
        assertEquals(visibility, actual);
    }

    @Test
    void getCloudLayers() {
        List<CloudLayer> actual = underTest.getCloudLayers();
        assertEquals(List.of(cloudLayer), actual);
    }

    @Test
    void getWind() {
        Wind actual = underTest.getWind();
        assertEquals(wind, actual);
    }

    @Test
    void builder() {
        Current current = Current.builder()
                .ident("ident")
                .cloudLayers(List.of(cloudLayer))
                .temperature(1.0)
                .relativeHumidity(1.0)
                .visibility(visibility)
                .wind(wind)
                .build();
        assertEquals("ident", current.getIdent());
        assertEquals(List.of(cloudLayer), current.getCloudLayers());
        assertEquals(1.0, current.getTemperature());
        assertEquals(1.0, current.getRelativeHumidity());
        assertEquals(visibility, current.getVisibility());
        assertEquals(wind, current.getWind());
    }

    @Test
    void  builder_noArgs() {
        Current current = Current.builder().build();
        assertNull(current.getIdent());
        assertNull(current.getCloudLayers());
        assertNull(current.getTemperature());
        assertNull(current.getRelativeHumidity());
        assertNull(current.getVisibility());
        assertNull(current.getWind());
    }

    @Test
    void no_args_constructor() {
        Current current = new Current();
        assertNull(current.getIdent());
        assertNull(current.getCloudLayers());
        assertNull(current.getTemperature());
        assertNull(current.getRelativeHumidity());
        assertNull(current.getVisibility());
        assertNull(current.getWind());
    }
}
