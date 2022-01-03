package com.application.bekend.DTO;

public class AdditionalServicesDTO {

    private Long id;
    private String name;
    private float price;

    public AdditionalServicesDTO(Long id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public AdditionalServicesDTO() {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
