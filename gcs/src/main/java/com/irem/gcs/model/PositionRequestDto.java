package com.irem.gcs.model;

import lombok.Data;
@Data
public class PositionRequestDto {
    private int satelliteID;
    private float observerLat;
    private float observerLng;
    private float observerAlt;
    private int seconds;
}

