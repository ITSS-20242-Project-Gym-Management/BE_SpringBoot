package com.example.itssprj_ver1.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="review")
public class review {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    private customer customer;

    @Column(name = "text",nullable = false)
    private String text;

    @Column(name = "createAt",nullable = false)
    private Date createAt;

}
