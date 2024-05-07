package com.irem.gcs.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "positions")
public class Position {
    @Id
    private String id;
    private int satid;
    private String satname;
    private int transactionscount;
    private float satlatitude;
    private float satlongitude;
    private float azimuth;
    private float elevation;
    private float ra;
    private float dec;
    private int timestamp;
}
