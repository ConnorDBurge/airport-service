package com.foreflight.airport.runway;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunwayDTO {

    private String ident;
    private String name;
    private Float latitudeBase;
    private Float longitudeBase;
    private String recipName;
    private Float longitudeRecip;
    private Float latitudeRecip;

    public static RunwayDTO fromEntity(Runway runway) {
        return RunwayDTO.builder()
                .ident(runway.getIdent())
                .name(runway.getName())
                .latitudeBase(runway.getLatitudeBase())
                .longitudeBase(runway.getLongitudeBase())
                .recipName(runway.getRecipName())
                .longitudeRecip(runway.getLongitudeRecip())
                .latitudeRecip(runway.getLatitudeRecip())
                .build();
    }

    public static List<RunwayDTO> fromEntities(List<Runway> runway) {
        return runway.stream()
                .map(RunwayDTO::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }
}
