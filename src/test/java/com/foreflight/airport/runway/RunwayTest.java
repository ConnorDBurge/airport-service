package com.foreflight.airport.runway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunwayTest {

    private Runway underTest;

    @BeforeEach
    void setUp() {
        underTest = Runway.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .recipName("KFFC")
                .build();
    }

    @Test
    void getIdent() {
        String actual = underTest.getIdent();
        assertEquals("KFFC", actual);
    }

    @Test
    void getName() {
        String actual = underTest.getName();
        assertEquals("Atlanta Regional Airport - Falcon Field", actual);
    }

    @Test
    void getRecipName() {
        String actual = underTest.getRecipName();
        assertEquals("KFFC", actual);
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
}
