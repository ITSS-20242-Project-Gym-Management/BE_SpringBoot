package com.example.itssprj_ver1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name="exerciseSession")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class exerciseSession {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "trainerid", nullable = true)
    private staff staff;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = true)
    private customer customer;

    @Column(name = "ExerciseType",nullable = true)
    private String exerciseType;

    @Column(name = "beginAt",nullable = true)
    private LocalDateTime beginAt;

    @Column(name = "endAt",nullable = true)
    private LocalDateTime endAt;

    @Column(name = "description",nullable = true)
    private String description;

}
