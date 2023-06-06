package com.wcc.geodistance.service;


import com.wcc.geodistance.entity.PostcodeEntity;
import com.wcc.geodistance.exception.PostcodeNotFoundException;
import com.wcc.geodistance.repository.PostcodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DistanceServiceIntegrationTest {
    @Autowired
    private DistanceService distanceService;

    @Autowired
    private PostcodeRepository postcodeRepository;

    @Test
    public void testCalculateDistance() throws PostcodeNotFoundException {

        var listOfEntity = List.of(new PostcodeEntity(1, "AB12 3CD", 57.1234, -2.3456),
                new PostcodeEntity(2, "XY34 5ZA", 54.9876, -1.2345));

        postcodeRepository.saveAll(listOfEntity);

        var response = distanceService.calculateDistance("AB12 3CD", "XY34 5ZA");

        assertNotNull(response);
        assertEquals("AB12 3CD", response.postalCode1());
        assertEquals(57.1234, response.latitude1());
        assertEquals(-2.3456, response.longitude1());
        assertEquals("XY34 5ZA", response.postalCode2());
        assertEquals(54.9876, response.latitude2());
        assertEquals(-2.3456, response.longitude1());
        assertTrue(response.distance() > 0);
        assertEquals("km", response.unit());

    }
}
