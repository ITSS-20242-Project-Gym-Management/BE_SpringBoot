package com.example.itssprj_ver1.model;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class roles {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer roleid;

    @Column(name = "rolename",nullable = false)
    private String rolename;


}
