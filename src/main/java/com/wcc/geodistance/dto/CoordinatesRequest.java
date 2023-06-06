package com.wcc.geodistance.dto;

import lombok.Builder;

@Builder
public record CoordinatesRequest(double latitude, double longitude) {
}