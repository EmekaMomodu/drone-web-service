package com.emekamomodu.dronewebservice.model;

import com.emekamomodu.dronewebservice.entity.Medication;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/25/22 9:27 PM
 */
public class MedicationLoadedModel extends MedicationModel {

    private Integer frequency;

    public MedicationLoadedModel(Integer frequency) {
        this.frequency = frequency;
    }

    public MedicationLoadedModel(Medication medication, Integer frequency) {
        super(medication);
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

}
