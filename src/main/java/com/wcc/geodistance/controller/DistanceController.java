package com.wcc.geodistance.controller;


import com.wcc.geodistance.dto.CoordinatesRequest;
import com.wcc.geodistance.dto.DistanceResponse;
import com.wcc.geodistance.exception.PostcodeNotFoundException;
import com.wcc.geodistance.service.DistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distance")
@RequiredArgsConstructor
public class DistanceController {
    private final DistanceService distanceService;

    @GetMapping("/{postcode1}/{postcode2}")
    public ResponseEntity<DistanceResponse> calculateDistance(
            @PathVariable String postcode1, @PathVariable String postcode2) throws PostcodeNotFoundException {
        DistanceResponse response = distanceService.calculateDistance(postcode1, postcode2);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postCode}")
    public ResponseEntity<String> updateCoordinates(
            @PathVariable String postCode, @RequestBody CoordinatesRequest coordinatesRequest) throws PostcodeNotFoundException {
        distanceService.updateCoordinates(postCode, coordinatesRequest.latitude(), coordinatesRequest.longitude());
        return ResponseEntity.ok("Coordinates updated successfully");
    }

    @DeleteMapping("/{postCode}")
    public ResponseEntity<String> deletePostCode(@PathVariable String postCode) throws PostcodeNotFoundException {
        distanceService.deletePostcode(postCode);
        return ResponseEntity.ok("Postcode deleted successfully");
    }
}