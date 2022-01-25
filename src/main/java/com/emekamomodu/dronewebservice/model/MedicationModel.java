package com.emekamomodu.dronewebservice.model;


import com.emekamomodu.dronewebservice.entity.Medication;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 9:49 AM
 */
public class MedicationModel {

    private Long id;

    private String name;

    private Integer weight;

    private String code;

    public MedicationModel() {
    }

    public MedicationModel(Medication medication) {
        this.id = medication.getMedicationId();
        this.name = medication.getName();
        this.weight = medication.getWeight();
        this.code = medication.getCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MedicationModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", code='" + code + '\'' +
                '}';
    }
}
