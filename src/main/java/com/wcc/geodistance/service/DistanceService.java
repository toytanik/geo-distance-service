package com.wcc.geodistance.service;

import com.wcc.geodistance.dto.DistanceResponse;
import com.wcc.geodistance.entity.PostcodeEntity;
import com.wcc.geodistance.exception.PostcodeNotFoundException;
import com.wcc.geodistance.repository.PostcodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistanceService {
    private static final double EARTH_RADIUS = 6371; // radius in kilometers
    private final PostcodeRepository postcodeRepository;

    public DistanceResponse calculateDistance(String postcode1, String postcode2)
            throws PostcodeNotFoundException {

        log.info("Calculating distance between postal codes: {} and {}", postcode1, postcode2);

        var postcodeEntities = postcodeRepository.findByPostcodeIn(List.of(postcode1,postcode2))
                .orElseThrow(() -> new PostcodeNotFoundException("Postcode not found: " + postcode1)); //mapper

        double distance = calculateDistance(postcodeEntities);
        log.info("Distance calculated successfully between postal codes: {} and {}", postcode1, postcode2);

        return createFromPostcodeEntities(postcodeEntities, distance);
    }

    public void updateCoordinates(String postcode, double latitude, double longitude) throws PostcodeNotFoundException {
        var entity = postcodeRepository.findByPostcode(postcode)
                .orElseThrow(() -> new PostcodeNotFoundException("Postcode not found: " + postcode));

        entity.setLatitude(latitude);
        entity.setLongitude(longitude);
        postcodeRepository.save(entity);

        log.info("Coordinates updated for postal code: {}", postcode);
    }

    public void deletePostcode(String postcode) throws PostcodeNotFoundException {
        var entity = postcodeRepository.findByPostcode(postcode)
                .orElseThrow(() -> new PostcodeNotFoundException("Postcode not found: " + postcode));

        postcodeRepository.delete(entity);

        log.info("Postal code deleted: {}", postcode);
    }

    private double calculateDistance(List<PostcodeEntity> postcodeEntities) {
        // Using Haversine formula
        double lon1Radians = Math.toRadians(postcodeEntities.get(0).getLongitude());
        double lon2Radians = Math.toRadians(postcodeEntities.get(1).getLongitude());
        double lat1Radians = Math.toRadians(postcodeEntities.get(0).getLatitude());
        double lat2Radians = Math.toRadians(postcodeEntities.get(1).getLatitude());

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

    public static DistanceResponse createFromPostcodeEntities(List<PostcodeEntity> postcodeEntities, double distance) {
        if (postcodeEntities.size() < 2) {
            throw new IllegalArgumentException("At least two postcode entities are required.");
        }

        PostcodeEntity entity1 = postcodeEntities.get(0);
        PostcodeEntity entity2 = postcodeEntities.get(1);

        return new DistanceResponse(
                entity1.getPostcode(),
                entity1.getLatitude(),
                entity1.getLongitude(),
                entity2.getPostcode(),
                entity2.getLatitude(),
                entity2.getLongitude(),
                distance,
                "km"
        );
    }
}