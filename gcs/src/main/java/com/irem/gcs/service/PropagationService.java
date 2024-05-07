package com.irem.gcs.service;

import com.irem.gcs.converter.PositionConverter;
import com.irem.gcs.entity.Position;
import com.irem.gcs.feign.N2YOFeignClient;
import com.irem.gcs.model.PassesRequestDto;
import com.irem.gcs.model.PositionRequestDto;
import com.irem.gcs.model.PositionResponseDto;
import com.irem.gcs.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropagationService {
    private final N2YOFeignClient n2yoFeignClient;
    private final PositionRepository positionRepository;
    private final PositionConverter positionConverter;

    private final String apiKey = "FMT35F-HXHULX-EX5U6S-588B";
    public Object getTLE(int satelliteID){
        ResponseEntity<Object> tle = n2yoFeignClient.getTLE(satelliteID, apiKey);
        return tle;
    }
    public Object getPosition(PositionRequestDto positionRequestDto){
        ResponseEntity<PositionResponseDto> responseDtoResponseEntity = n2yoFeignClient.getPosition(positionRequestDto.getSatelliteID(), positionRequestDto.getObserverLat(), positionRequestDto.getObserverLng(), positionRequestDto.getObserverAlt(), positionRequestDto.getSeconds(), apiKey);
        List<Position> positionList = positionConverter.toEntity(responseDtoResponseEntity.getBody());
        boolean isSaved = savePositions(positionList);
        return positionList;
    }
    public Object getPasses(PassesRequestDto passesRequestDto){
        ResponseEntity<Object> passes = n2yoFeignClient.getPasses(passesRequestDto.getSatelliteID(), passesRequestDto.getObserverLat(), passesRequestDto.getObserverLng(), passesRequestDto.getObserverAlt(), passesRequestDto.getDays(), passesRequestDto.getMinVisibility(), apiKey);
        return passes;
    }

    public boolean savePositions(List<Position> positionList){
        List<Position> positions = positionRepository.saveAll(positionList);
        return positions.size() > 0;
    }

}
