package com.emekamomodu.dronewebservice.model;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/23/22 8:57 PM
 */
public class LoadDroneMedicationModel {

    private Long medicationId;

    private Integer frequency;

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "LoadDroneMedicationModel{" +
                "medicationId=" + medicationId +
                ", frequency=" + frequency +
                '}';
    }

}
