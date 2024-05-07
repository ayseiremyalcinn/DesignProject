package com.irem.gcs.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "telemetries")
public class Telemetry {
    @Id
    private String id;
    private String time;
    private int tec;
    private float temperature;
    private float bateryVoltage;
}
