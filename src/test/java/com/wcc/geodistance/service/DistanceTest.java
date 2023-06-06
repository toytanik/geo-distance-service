package com.wcc.geodistance.service;

import com.wcc.geodistance.controller.DistanceController;
import com.wcc.geodistance.dto.CoordinatesRequest;
import com.wcc.geodistance.entity.PostcodeEntity;
import com.wcc.geodistance.exception.PostcodeNotFoundException;
import com.wcc.geodistance.repository.PostcodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DistanceTest {
    @Mock
    private PostcodeRepository postcodeRepository;

    @InjectMocks
    private DistanceService distanceService;

    private DistanceController distanceController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        distanceController = new DistanceController(distanceService);
    }

    @Test
    public void testCalculateDistance() throws PostcodeNotFoundException {
        var entity1 = new PostcodeEntity(1, "AB12 3CD", 57.1234, -2.3456);
        var entity2 = new PostcodeEntity(2, "XY34 5ZA", 54.9876, -1.2345);

        when(postcodeRepository.findByPostcode("AB12 3CD")).thenReturn(Optional.of(entity1));
        when(postcodeRepository.findByPostcode("XY34 5ZA")).thenReturn(Optional.of(entity2));

        var responseEntity = distanceController.calculateDistance("AB12 3CD", "XY34 5ZA");
        var response = responseEntity.getBody();

        // Assert the response contains the expected values
        assert response != null;
        assert response.postalCode1().equals("AB12 3CD");
        assert response.latitude1() == 57.1234;
        assert response.longitude1() == -2.3456;
        assert response.postalCode2().equals("XY34 5ZA");
        assert response.latitude2() == 54.9876;
        assert response.longitude2() == -1.2345;

        verify(postcodeRepository, times(1)).findByPostcode("AB12 3CD");
        verify(postcodeRepository, times(1)).findByPostcode("XY34 5ZA");
    }

    @Test
    public void testUpdateCoordinates() throws PostcodeNotFoundException {
        var entity = new PostcodeEntity(1, "AB12 3CD", 0.0, 0.0);

        when(postcodeRepository.findByPostcode("AB12 3CD")).thenReturn(Optional.of(entity));

        var coordinatesRequest = new CoordinatesRequest(57.1234, -2.3456);
        var responseEntity = distanceController.updateCoordinates("AB12 3CD", coordinatesRequest);
        var response = responseEntity.getBody();

        // Assert the response is successful
        assert response != null;
        assert response.equals("Coordinates updated successfully");

        // Verify the coordinates were updated in the entity
        assert entity.getLatitude() == 57.1234;
        assert entity.getLongitude() == -2.3456;

        verify(postcodeRepository, times(1)).findByPostcode("AB12 3CD");
        verify(postcodeRepository, times(1)).save(entity);
    }

    @Test
    public void testDeletePostalCode() throws PostcodeNotFoundException {
        var entity = new PostcodeEntity(1, "AB12 3CD", 0.0, 0.0);

        when(postcodeRepository.findByPostcode("AB12 3CD")).thenReturn(Optional.of(entity));

        var responseEntity = distanceController.deletePostCode("AB12 3CD");
        var response = responseEntity.getBody();

        assertNotNull(response);
        assertEquals("Postcode deleted successfully", response);

        verify(postcodeRepository, times(1)).findByPostcode("AB12 3CD");
        verify(postcodeRepository, times(1)).delete(entity);
    }
}
