package com.irem.gcs.model;

import lombok.Data;

@Data
public class PositionDto {
    private float satlatitude;
    private float satlongitude;
    private float azimuth;
    private float elevation;
    private float ra;
    private float dec;
    private int timestamp;
}
