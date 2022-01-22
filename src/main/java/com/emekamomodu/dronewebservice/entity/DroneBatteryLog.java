package com.emekamomodu.dronewebservice.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 4:22 PM
 */
@Entity
@Table(name = "drone_battery_log")
public class DroneBatteryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "drone_battery_log_id")
    private Long droneBatteryLogId;

    @Column(name = "battery_capacity", nullable = false)
    private Integer batteryCapacity;

    @Column(name = "log_date", nullable = false)
    private LocalDateTime logDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    public Long getDroneBatteryLogId() {
        return droneBatteryLogId;
    }

    public void setDroneBatteryLogId(Long droneBatteryLogId) {
        this.droneBatteryLogId = droneBatteryLogId;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

}
