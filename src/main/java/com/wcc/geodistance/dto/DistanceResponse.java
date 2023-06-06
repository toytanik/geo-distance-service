package com.wcc.geodistance.dto;


public record DistanceResponse(
        String postalCode1,
        double latitude1,
        double longitude1,
        String postalCode2,
        double latitude2,
        double longitude2,
        double distance,
        String unit
) {
    // You can add additional methods or behavior specific to the class if needed
}
