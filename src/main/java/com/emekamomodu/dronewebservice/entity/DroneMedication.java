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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "load_date", nullable = false)
    private LocalDateTime loadDate;

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

    public LocalDateTime getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(LocalDateTime loadDate) {
        this.loadDate = loadDate;
    }

}
