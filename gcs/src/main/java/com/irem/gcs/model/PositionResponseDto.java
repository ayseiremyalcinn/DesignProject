package com.irem.gcs.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PositionResponseDto {
   private InfoResponseDto info;
   private List<PositionDto> positions;
}
