package com.wcc.geodistance.controller;

import com.wcc.geodistance.dto.CoordinatesRequest;
import com.wcc.geodistance.dto.DistanceResponse;
import com.wcc.geodistance.exception.PostcodeNotFoundException;
import com.wcc.geodistance.service.DistanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.mockito.Mockito.*;

public class DistanceControllerTests {
    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private DistanceController distanceController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateDistance() throws PostcodeNotFoundException {
        DistanceResponse expectedResponse = new DistanceResponse("AB12 3CD", 57.1234, -2.3456,
                "XY34 5ZA", 54.9876, -1.2345, 123.45, "km");

        when(distanceService.calculateDistance("AB12 3CD", "XY34 5ZA")).thenReturn(expectedResponse);

        var responseEntity = distanceController.calculateDistance("AB12 3CD", "XY34 5ZA");
        var response = responseEntity.getBody();

        // Assert the response is successful
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert response != null;
        assert response.equals(expectedResponse);

        verify(distanceService, times(1)).calculateDistance("AB12 3CD", "XY34 5ZA");
    }

    @Test
    public void testUpdateCoordinates() throws PostcodeNotFoundException {
        var coordinatesRequest = new CoordinatesRequest(57.1234, -2.3456);
        var expectedResponse = new ResponseEntity<>("Coordinates updated successfully", HttpStatus.OK);

        var responseEntity = distanceController.updateCoordinates("AB12 3CD", coordinatesRequest);

        // Assert the response is successful
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert Objects.equals(responseEntity.getBody(), expectedResponse.getBody());

        verify(distanceService, times(1)).updateCoordinates("AB12 3CD", 57.1234, -2.3456);
    }

    @Test
    public void testDeletePostalCode() throws PostcodeNotFoundException {
        var expectedResponse = new ResponseEntity<>("Postcode deleted successfully", HttpStatus.OK);

        var responseEntity = distanceController.deletePostCode("AB12 3CD");

        // Assert the response is successful
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert Objects.equals(responseEntity.getBody(), expectedResponse.getBody());

        verify(distanceService, times(1)).deletePostcode("AB12 3CD");
    }
}
