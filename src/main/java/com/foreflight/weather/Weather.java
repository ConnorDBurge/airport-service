package com.foreflight.weather;

import com.foreflight.weather.report.Report;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    @Setter
    private String ident;
    private Report report;
    private List<String> remarks = new ArrayList<>();
    @Setter
    private Double lat;
    @Setter
    private Double lon;

    public void addRemark(String remark) {
        remarks.add(remark);
    }
}
