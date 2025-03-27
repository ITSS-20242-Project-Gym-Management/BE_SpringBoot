package com.example.itssprj_ver1.model;

import jakarta.persistence.*;

@Entity
@Table(name="membership")
public class membership {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description",nullable = true)
    private String description;

    @Column(name = "exerciseType",nullable = false)
    private String exerciseType;

    @Column(name = "price",nullable = false)
    private Float price;

}
