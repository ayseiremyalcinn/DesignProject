package com.irem.gcs.model;

import lombok.Data;

@Data
public class PassesRequestDto {
    private int satelliteID;
    private float observerLat;
    private float observerLng;
    private float observerAlt;
    private int days;
    int minVisibility;
}
