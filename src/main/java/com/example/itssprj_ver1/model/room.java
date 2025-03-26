package com.example.itssprj_ver1.model;

import jakarta.persistence.*;

@Entity
@Table(name="room")
public class room {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "status",nullable = false)
    private String status;

}
