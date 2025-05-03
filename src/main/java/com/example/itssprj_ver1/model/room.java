package com.example.itssprj_ver1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="room")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class room {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "status",nullable = false)
    private String status;

}
