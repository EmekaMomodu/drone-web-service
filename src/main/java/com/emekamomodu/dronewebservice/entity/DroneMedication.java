package com.emekamomodu.dronewebservice.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 8:56 PM
 */
@Entity
@Table(name = "drone_medication")
public class DroneMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drone_medication_id")
    private Long droneMedicationId;

    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(name = "medication_frequency", nullable = false)
    private Integer medicationFrequency;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public DroneMedication() {
    }

    public DroneMedication(Drone drone, Medication medication, Integer frequency) {
        this.drone = drone;
        this.medication = medication;
        this.medicationFrequency = frequency;
    }

    public Long getDroneMedicationId() {
        return droneMedicationId;
    }

    public void setDroneMedicationId(Long droneMedicationId) {
        this.droneMedicationId = droneMedicationId;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Integer getMedicationFrequency() {
        return medicationFrequency;
    }

    public void setMedicationFrequency(Integer medicationFrequency) {
        this.medicationFrequency = medicationFrequency;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime loadDate) {
        this.createDate = loadDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
