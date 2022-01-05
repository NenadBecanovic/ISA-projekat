package com.application.bekend.DTO;

import java.util.Set;

public class HouseReservationDTO {

    private Long houseId;
    private Long id;
    private String startDate;
    private String endDate;
    private int maxGuests;
    private float price;
    private boolean isAvailable;
    private Set<AdditionalServicesDTO> additionalServices;  // unutar DTO objekta mogu biti samo primitivni tipovi ili neki drugi DTO (ne sme biti model)


    public HouseReservationDTO(){};

    public HouseReservationDTO(Long houseId, Long id, String startDate, String endDate, int maxGuests, float price, boolean isAvailable) {
        this.houseId = houseId;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public HouseReservationDTO(Long id, String startDate, String endDate, int maxGuests, float price, boolean isAvailable) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
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

    public Set<AdditionalServicesDTO> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalServicesDTO> additionalServices) {
        this.additionalServices = additionalServices;
    }
}
