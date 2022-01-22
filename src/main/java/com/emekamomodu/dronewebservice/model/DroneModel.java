package com.emekamomodu.dronewebservice.model;

import com.emekamomodu.dronewebservice.entity.Drone;
import com.emekamomodu.dronewebservice.model.enums.EDroneModel;
import com.emekamomodu.dronewebservice.model.enums.EDroneState;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/21/22 12:21 AM
 */
public class DroneModel {

    private Long droneId;

    private String serialNumber;

    private EDroneModel model;

    private Integer weightLimit;

    private Integer batteryCapacity;

    private EDroneState state;

    private Integer availableWeight;

    public DroneModel() {
    }

    public DroneModel(Drone drone) {
        this.droneId = drone.getDroneId();
        this.serialNumber = drone.getSerialNumber();
        this.model = drone.getModel();
        this.weightLimit = drone.getWeightLimit();
        this.batteryCapacity = drone.getBatteryCapacity();
        this.state = drone.getState();
        this.availableWeight = drone.getAvailableWeight();
    }

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

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
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

    @Override
    public String toString() {
        return "DroneModel{" +
                "droneId=" + droneId +
                ", serialNumber='" + serialNumber + '\'' +
                ", model=" + model +
                ", weightLimit=" + weightLimit +
                ", batteryCapacity=" + batteryCapacity +
                ", state=" + state +
                ", availableWeight=" + availableWeight +
                '}';
    }

}
