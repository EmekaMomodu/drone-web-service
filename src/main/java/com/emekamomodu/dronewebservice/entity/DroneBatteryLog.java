package com.emekamomodu.dronewebservice.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Column(name = "battery_capacity", scale = 2, nullable = false)
    private Float batteryCapacity;

    @Column(name = "log_date", nullable = false)
    private LocalDateTime logDate;

    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    public Long getDroneBatteryLogId() {
        return droneBatteryLogId;
    }

    public void setDroneBatteryLogId(Long droneBatteryLogId) {
        this.droneBatteryLogId = droneBatteryLogId;
    }

    public Float getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Float batteryCapacity) {
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
