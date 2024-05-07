package com.irem.gcs.converter;

import com.irem.gcs.entity.Position;
import com.irem.gcs.model.PositionDto;
import com.irem.gcs.model.PositionResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PositionConverter {
    public List<Position> toEntity(PositionResponseDto positionResponseDto){
        List<Position> positionList = new ArrayList<>();
        for(PositionDto positionDto: positionResponseDto.getPositions()){
            Position position = Position.builder().satid(positionResponseDto.getInfo().getSatid())
                    .satname(positionResponseDto.getInfo().getSatname())
                    .transactionscount(positionResponseDto.getInfo().getTransactionscount())
                    .satlatitude(positionDto.getSatlatitude())
                    .satlongitude(positionDto.getSatlongitude())
                    .azimuth(positionDto.getAzimuth())
                    .elevation(positionDto.getElevation())
                    .ra(positionDto.getRa())
                    .dec(positionDto.getDec())
                    .timestamp(positionDto.getTimestamp()).build();
            positionList.add(position);
        }
        return positionList;
    }
}
