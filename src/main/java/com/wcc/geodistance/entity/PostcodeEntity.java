package com.wcc.geodistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "postcodelatlng")
public class PostcodeEntity {
    @Id
    private Integer id;
    private String postcode;
    private double latitude;
    private double longitude;
}
