package com.emekamomodu.dronewebservice.entity;

import com.emekamomodu.dronewebservice.model.enums.EDroneModel;
import com.emekamomodu.dronewebservice.model.enums.EDroneState;

import javax.persistence.*;
import java.util.List;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/17/22 12:29 AM
 */
@Entity
@Table(name = "drone")
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "drone_id")
    private Long droneId;

    @Column(name = "serial_number", unique = true,  length = 100, nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model", nullable = false)
    private EDroneModel model;

    @Column(name = "weight_limit", nullable = false)
    private Integer weightLimit;

    @Column(name = "battery_capacity", scale = 2, nullable = false)
    private Float batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private EDroneState state;

    @Column(name = "available_weight", nullable = false)
    private Integer availableWeight;

    @ManyToMany
    @JoinTable(name = "drones_medications", joinColumns = @JoinColumn(name = "drone_id", referencedColumnName = "drone_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "medication_id"))
    private List<Medication> dronesMedications;

    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public EDroneModel getModel() {
        return model;
    }

    public void setModel(EDroneModel model) {
        this.model = model;
    }

    public Integer getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Integer weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Float getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public EDroneState getState() {
        return state;
    }

    public void setState(EDroneState state) {
        this.state = state;
    }

    public Integer getAvailableWeight() {
        return availableWeight;
    }

    public void setAvailableWeight(Integer availableWeight) {
        this.availableWeight = availableWeight;
    }

}
