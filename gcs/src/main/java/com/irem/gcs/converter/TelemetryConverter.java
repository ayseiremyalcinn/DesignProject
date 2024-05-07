package com.irem.gcs.converter;

import com.irem.gcs.entity.Telemetry;
import org.springframework.stereotype.Component;


@Component
public class TelemetryConverter {
    public Telemetry toTelemetry(String str){
        String[] arr = str.split(",");
        Telemetry tm = Telemetry.builder()
                .time(arr[0])
                .tec(Integer.parseInt(arr[1]))
                .temperature(Float.parseFloat(arr[2]))
                .bateryVoltage(Float.parseFloat(arr[3]))
                .build();
        return tm;
    }
}
