package com.emekamomodu.dronewebservice.model;

import java.util.Arrays;

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

    private byte[] image;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "MedicationModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", code='" + code + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

}
