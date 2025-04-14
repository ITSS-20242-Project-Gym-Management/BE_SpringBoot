package com.example.itssprj_ver1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name="payment")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class payment {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    private customer customer;

    @Column(name = "amount",nullable = false)
    private Float amount;

    @Column(name = "method",nullable = false)
    private String method;

    @Column(name = "paid",nullable = false)
    private Boolean paid;

    @Column(name = "createAt",nullable = false)
    private Date createAt;



}
