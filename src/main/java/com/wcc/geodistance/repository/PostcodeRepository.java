package com.wcc.geodistance.repository;

import com.wcc.geodistance.entity.PostcodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostcodeRepository extends JpaRepository<PostcodeEntity, Integer> {
    Optional<PostcodeEntity> findByPostcode(String postalCode1);

    Optional<List<PostcodeEntity>> findByPostcodeIn(List<String> postalCodes);
}
