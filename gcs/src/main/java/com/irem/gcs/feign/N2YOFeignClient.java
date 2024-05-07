package com.irem.gcs.feign;

import com.irem.gcs.model.PositionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "afdfa", url = "https://api.n2yo.com/rest/v1/satellite")
public interface N2YOFeignClient {
    @GetMapping("/tle/{satelliteID}")
    ResponseEntity<Object> getTLE(@PathVariable("satelliteID") int satelliteID,
                                  @RequestParam("apiKey") String apiKey);

    @GetMapping("/positions/{satelliteID}/{observerLat}/{observerLng}/{observerAlt}/{seconds}")
    ResponseEntity<PositionResponseDto> getPosition(@PathVariable("satelliteID") int satelliteID,
                                                    @PathVariable("observerLat") float observerLat,
                                                    @PathVariable("observerLng") float observerLng,
                                                    @PathVariable("observerAlt") float observerAlt,
                                                    @PathVariable("seconds") int seconds,
                                                    @RequestParam("apiKey") String apiKey);

    @GetMapping("/visualpasses/{satelliteID}/{observerLat}/{observerLng}/{observerAlt}/{days}/{minVisibility}")
    ResponseEntity<Object> getPasses(@PathVariable("satelliteID") int satelliteID,
                                       @PathVariable("observerLat") float observerLat,
                                       @PathVariable("observerLng") float observerLng,
                                       @PathVariable("observerAlt") float observerAlt,
                                       @PathVariable("days") int days,
                                       @PathVariable("minVisibility") int minVisibility,
                                       @RequestParam("apiKey") String apiKey);
}


