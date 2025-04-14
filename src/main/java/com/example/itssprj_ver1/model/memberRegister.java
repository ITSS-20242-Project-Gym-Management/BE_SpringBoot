package com.example.itssprj_ver1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name="memberRegister")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class memberRegister {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "membershipid", nullable = false)
    private membership membership;

    @ManyToOne
    @JoinColumn(name = "memberid", nullable = false)
    private customer customer;

    @Column(name = "createAt",nullable = false)
    private Date createAt;

    @Column(name = "status",nullable = false)
    private String status;

    @Column(name = "beginAt",nullable = true)
    private Date beginAt;

    @Column(name = "endAt",nullable = true)
    private Date endAt;


}
