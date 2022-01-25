package com.emekamomodu.dronewebservice.model;

import java.util.List;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/23/22 7:32 PM
 */
public class LoadDroneModel {

    private Long droneId;

    private List<LoadDroneMedicationModel> medicationList;

    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public List<LoadDroneMedicationModel> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<LoadDroneMedicationModel> medicationList) {
        this.medicationList = medicationList;
    }

    @Override
    public String toString() {
        return "LoadDroneModel{" +
                "droneId=" + droneId +
                ", medicationList=" + medicationList +
                '}';
    }

}
