package com.example.itssprj_ver1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="membership")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
