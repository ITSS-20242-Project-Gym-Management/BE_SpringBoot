package com.example.itssprj_ver1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="memberRegister")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
    private LocalDate beginAt;

    @Column(name = "endAt",nullable = true)
    private LocalDate endAt;


}
