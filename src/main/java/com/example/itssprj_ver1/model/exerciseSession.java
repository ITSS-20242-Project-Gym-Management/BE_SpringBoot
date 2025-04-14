package com.example.itssprj_ver1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="exerciseSession")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private String ExerciseType;

    @Column(name = "beginAt",nullable = false)
    private LocalDateTime beginAt;

    @Column(name = "endAt",nullable = false)
    private LocalDateTime endAt;

    @Column(name = "description",nullable = true)
    private String description;

}
