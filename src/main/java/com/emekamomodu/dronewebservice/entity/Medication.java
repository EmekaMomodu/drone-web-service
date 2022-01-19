package com.emekamomodu.dronewebservice.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/17/22 2:35 AM
 */
@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medication_id")
    private Long medicationId;

    @Column(name = "name", unique = true,  nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Lob
    @Column(name = "image", unique = true, nullable = false)
    private byte[] image;

    @ManyToMany(mappedBy = "dronesMedications")
    private List<Drone> drones;

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public void setDrones(List<Drone> drones) {
        this.drones = drones;
    }
}
