package com.application.bekend.DTO;

import java.util.Date;

public class HouseReservationDTO {

    private Long houseId;
    private Long id;
    private String startDate;
    private String endDate;
    private int maxGuests;
    private String additionalServices;
    private float price;
    private boolean isAvailable;

    public HouseReservationDTO(){};

    public HouseReservationDTO(Long houseId, Long id, String startDate, String endDate, int maxGuests, String additionalServices, float price, boolean isAvailable) {
        this.houseId = houseId;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.additionalServices = additionalServices;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public HouseReservationDTO(Long id, String startDate, String endDate, int maxGuests, String additionalServices, float price, boolean isAvailable) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.additionalServices = additionalServices;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(String additionalServices) {
        this.additionalServices = additionalServices;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
}
