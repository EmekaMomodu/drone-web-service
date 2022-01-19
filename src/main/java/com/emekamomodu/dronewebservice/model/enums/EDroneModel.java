package com.emekamomodu.dronewebservice.model.enums;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/17/22 12:53 AM
 */
public enum EDroneModel {

    LIGHT_WEIGHT(150),
    MIDDLE_WEIGHT(250),
    CRUISER_WEIGHT(350),
    HEAVY_WEIGHT(500);

    private final Integer maximumWeight;

    EDroneModel(Integer maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public Integer getMaximumWeight() {
        return maximumWeight;
    }

}
