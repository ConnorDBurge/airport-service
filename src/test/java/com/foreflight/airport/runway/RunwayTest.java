package com.foreflight.airport.runway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunwayTest {

    private Runway underTest;

    @BeforeEach
    void setUp() {
        underTest = Runway.builder()
                .ident("13-31")
                .name("Atlanta Regional Airport - Falcon Field")
                .magneticHeading(130)
                .recipMagneticHeading(310)
                .recipName("31")
                .crossWind(10.0)
                .headWind(10.0)
                .bestRunway(true)
                .build();
    }

    @Test
    void getIdent() {
        String actual = underTest.getIdent();
        assertEquals("13-31", actual);
    }

    @Test
    void getName() {
        String actual = underTest.getName();
        assertEquals("Atlanta Regional Airport - Falcon Field", actual);
    }

    @Test
    void getRecipName() {
        String actual = underTest.getRecipName();
        assertEquals("31", actual);
    }

    @Test
    void getMagneticHeading() {
        double actual = underTest.getMagneticHeading();
        assertEquals(130, actual);
    }

    @Test
    void getRecipMagneticHeading() {
        double actual = underTest.getRecipMagneticHeading();
        assertEquals(310, actual);
    }

    @Test
    void getCrossWind() {
        double actual = underTest.getCrossWind();
        assertEquals(10.0, actual);
    }

    @Test
    void getHeadWind() {
        double actual = underTest.getHeadWind();
        assertEquals(10.0, actual);
    }

    @Test
    void setCrossWind() {
        underTest.setCrossWind(20.0);
        double actual = underTest.getCrossWind();
        assertEquals(20.0, actual);
    }

    @Test
    void setHeadWind() {
        underTest.setHeadWind(20.0);
        double actual = underTest.getHeadWind();
        assertEquals(20.0, actual);
    }

    @Test
    void setBestRunway() {
        underTest.setBestRunway(false);
        assertFalse(underTest.isBestRunway());
    }

    @Test
    void builder() {
        Runway runway = Runway.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .recipName("KFFC")
                .build();
        assertEquals("KFFC", runway.getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", runway.getName());
        assertEquals("KFFC", runway.getRecipName());
    }

    @Test
    void builder_noArgs() {
        Runway runway = Runway.builder().build();
        assertNull(runway.getIdent());
        assertNull(runway.getName());
        assertNull(runway.getRecipName());
    }

    @Test
    void no_args_constructor() {
        Runway runway = new Runway();
        assertNull(runway.getIdent());
        assertNull(runway.getName());
        assertNull(runway.getRecipName());
    }

    @Test
    void compareTo() {
        Runway r1 = Runway.builder().ident("A").build();
        Runway r2 = Runway.builder().ident("B").build();
        Runway r3 = Runway.builder().ident("A").build();

        assertTrue(r1.compareTo(r2) < 0, "Expected r1 < r2");
        assertTrue(r2.compareTo(r1) > 0, "Expected r2 > r1");
        assertEquals(0, r1.compareTo(r3), "Expected r1 == r3");
    }
}
