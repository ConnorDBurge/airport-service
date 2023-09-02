package com.foreflight.airport.runway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RunwayDTOTest {

    private RunwayDTO underTest;

    @BeforeEach
    void setUp() {
        underTest = RunwayDTO.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .build();
    }

    @Test
    void fromEntity() {
        Runway runway = Runway.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .recipName("KFFC")
                .build();
        RunwayDTO runwayDTO = RunwayDTO.fromEntity(runway);
        assertEquals("KFFC", runwayDTO.getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", runwayDTO.getName());
    }

    @Test
    void fromEntities() {
        Runway kffc = Runway.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .recipName("KFFC")
                .build();
        Runway kauo = Runway.builder()
                .ident("KAUO")
                .name("Auburn University Regional Airport")
                .recipName("KAUO")
                .build();

        List<RunwayDTO> runwayDTOs = RunwayDTO.fromEntities(List.of(kffc, kauo));
        assertEquals(2, runwayDTOs.size());
        assertEquals("KFFC", runwayDTOs.get(0).getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", runwayDTOs.get(0).getName());
        assertEquals("KAUO", runwayDTOs.get(1).getIdent());
        assertEquals("Auburn University Regional Airport", runwayDTOs.get(1).getName());
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
    void builder() {
        RunwayDTO runwayDTO = RunwayDTO.builder()
                .ident("KFFC")
                .name("Atlanta Regional Airport - Falcon Field")
                .build();
        assertEquals("KFFC", runwayDTO.getIdent());
        assertEquals("Atlanta Regional Airport - Falcon Field", runwayDTO.getName());
    }

    @Test
    void builder_noArgs() {
        RunwayDTO runwayDTO = RunwayDTO.builder().build();
        assertNull(runwayDTO.getIdent());
        assertNull(runwayDTO.getName());
    }

    @Test
    void no_args_constructor() {
        RunwayDTO runwayDTO = new RunwayDTO();
        assertNull(runwayDTO.getIdent());
        assertNull(runwayDTO.getName());
    }
}
