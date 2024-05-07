package com.irem.gcs.controller;

import com.irem.gcs.model.PassesRequestDto;
import com.irem.gcs.model.PositionRequestDto;
import com.irem.gcs.service.PropagationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/")
@CrossOrigin("*")
public class PropagationController {
    @Autowired private PropagationService propagationService;

    @GetMapping("/tle")
    public ResponseEntity getTLE(@RequestParam int satelliteID){
        Object tle = propagationService.getTLE(satelliteID);
        return new ResponseEntity<>(tle, HttpStatus.OK);
    }
    @PostMapping("/positions")
    public ResponseEntity getPosition(@RequestBody PositionRequestDto positionRequestDto){
        Object position = propagationService.getPosition(positionRequestDto);
        return new ResponseEntity<>(position, HttpStatus.OK);
    }
    @PostMapping("/visualpasses")
    public ResponseEntity getPasses(@RequestBody PassesRequestDto passesRequestDto){
        Object passes = propagationService.getPasses(passesRequestDto);
        return new ResponseEntity<>(passes, HttpStatus.OK);
    }
}
