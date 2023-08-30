package com.foreflight.weather.report.current.cloudlayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudLayerTest {

    private CloudLayer underTest;

    @BeforeEach
    void setUp() {
        underTest = CloudLayer.builder()
                .coverage("coverage")
                .type("type")
                .altitudeFt(1.0)
                .ceiling(true)
                .build();
    }

    @Test
    void getCoverage() {
        String actual = underTest.getCoverage();
        assertEquals("coverage", actual);
    }

    @Test
    void getType() {
        String actual = underTest.getType();
        assertEquals("type", actual);
    }

    @Test
    void getAltitudeFt() {
        Double actual = underTest.getAltitudeFt();
        assertEquals(1.0, actual);
    }

    @Test
    void getCeiling() {
        Boolean actual = underTest.getCeiling();
        assertTrue(actual);
    }

    @Test
    void builder() {
        CloudLayer cloudLayer = CloudLayer.builder()
                .coverage("coverage")
                .type("type")
                .altitudeFt(1.0)
                .ceiling(true)
                .build();

        assertEquals("coverage", cloudLayer.getCoverage());
        assertEquals("type", cloudLayer.getType());
        assertEquals(1.0, cloudLayer.getAltitudeFt());
        assertTrue(cloudLayer.getCeiling());
    }

    @Test
    void builder_noArgs() {
        CloudLayer cloudLayer = CloudLayer.builder().build();
        assertNull(cloudLayer.getCoverage());
        assertNull(cloudLayer.getType());
        assertNull(cloudLayer.getAltitudeFt());
        assertNull(cloudLayer.getCeiling());
    }

    @Test
    void no_args_constructor() {
        CloudLayer cloudLayer = new CloudLayer();
        assertNull(cloudLayer.getCoverage());
        assertNull(cloudLayer.getType());
        assertNull(cloudLayer.getAltitudeFt());
        assertNull(cloudLayer.getCeiling());
    }
}
