package com.wcc.geodistance.service;

import com.wcc.geodistance.dto.DistanceResponse;
import com.wcc.geodistance.entity.PostcodeEntity;
import com.wcc.geodistance.exception.PostcodeNotFoundException;
import com.wcc.geodistance.repository.PostcodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistanceService {
    private static final double EARTH_RADIUS = 6371; // radius in kilometers
    private final PostcodeRepository postcodeRepository;

    public DistanceResponse calculateDistance(String postcode1, String postcode2)
            throws PostcodeNotFoundException {

        log.info("Calculating distance between postal codes: {} and {}", postcode1, postcode2);

        var postcodeEntity1 = postcodeRepository.findByPostcode(postcode1)
                .orElseThrow(() -> new PostcodeNotFoundException("Postcode not found: " + postcode1)); //mapper

        var postcodeEntity2 = postcodeRepository.findByPostcode(postcode2)
                .orElseThrow(() -> new PostcodeNotFoundException("Postcode not found: " + postcode2));

        double distance = calculateDistance(postcodeEntity1, postcodeEntity2);

        var response = new DistanceResponse(
                postcodeEntity1.getPostcode(), postcodeEntity1.getLatitude(), postcodeEntity1.getLongitude(),
                postcodeEntity2.getPostcode(), postcodeEntity2.getLatitude(), postcodeEntity2.getLongitude(),
                distance, "km");

        log.info("Distance calculated successfully between postal codes: {} and {}", postcode1, postcode2);

        return response;
    }

    public void updateCoordinates(String postcode, double latitude, double longitude) throws PostcodeNotFoundException {
        var entity = postcodeRepository.findByPostcode(postcode)
                .orElseThrow(() -> new PostcodeNotFoundException("Postcode not found: " + postcode));

        if (entity == null)
            throw new PostcodeNotFoundException("Postcode not found in the database");


        entity.setLatitude(latitude);
        entity.setLongitude(longitude);
        postcodeRepository.save(entity);

        log.info("Coordinates updated for postal code: {}", postcode);
    }

    public void deletePostcode(String postcode) throws PostcodeNotFoundException {
        var entity = postcodeRepository.findByPostcode(postcode)
                .orElseThrow(() -> new PostcodeNotFoundException("Postcode not found: " + postcode));

        if (entity == null)
            throw new PostcodeNotFoundException("Postcode not found in the database");


        postcodeRepository.delete(entity);

        log.info("Postal code deleted: {}", postcode);
    }

    private double calculateDistance(PostcodeEntity postcodeEntity1, PostcodeEntity postcodeEntity2) {
        // Using Haversine formula
        double lon1Radians = Math.toRadians(postcodeEntity1.getLongitude());
        double lon2Radians = Math.toRadians(postcodeEntity2.getLongitude());
        double lat1Radians = Math.toRadians(postcodeEntity1.getLatitude());
        double lat2Radians = Math.toRadians(postcodeEntity2.getLatitude());

        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }
}