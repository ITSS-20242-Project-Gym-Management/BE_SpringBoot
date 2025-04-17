package com.example.itssprj_ver1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roomEquipment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class roomEquipment {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "roomid", nullable = false)
    private room room;

    @Column(name = "equipmentName",nullable = false)
    private String equipmentName;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @Column(name = "status",nullable = false)
    private String status;

}
