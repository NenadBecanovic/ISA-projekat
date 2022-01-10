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
    private boolean availabilityPeriod = false;
    private boolean isAction = false;
    private Long guestId;
    private Double totalPrice;
    private String houseName;
    private Long milisStartDate;
    private Long milisEndDate;
    private boolean hasFeedbackOwner = false;
    private boolean hasFeedbackEntity = false;
    private boolean hasAppealOwner = false;
    private boolean hasAppealEntity = false;

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

    public boolean isAvailabilityPeriod() {
        return availabilityPeriod;
    }

    public void setAvailabilityPeriod(boolean availabilityPeriod) {
        this.availabilityPeriod = availabilityPeriod;
    }

    public boolean isAction() {
        return isAction;
    }

    public void setAction(boolean action) {
        isAction = action;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Long getMilisStartDate() {
        return milisStartDate;
    }

    public void setMilisStartDate(Long milisStartDate) {
        this.milisStartDate = milisStartDate;
    }

    public Long getMilisEndDate() {
        return milisEndDate;
    }

    public void setMilisEndDate(Long milisEndDate) {
        this.milisEndDate = milisEndDate;
    }

    public boolean isHasFeedbackOwner() {
        return hasFeedbackOwner;
    }

    public void setHasFeedbackOwner(boolean hasFeedbackOwner) {
        this.hasFeedbackOwner = hasFeedbackOwner;
    }

    public boolean isHasFeedbackEntity() {
        return hasFeedbackEntity;
    }

    public void setHasFeedbackEntity(boolean hasFeedbackEntity) {
        this.hasFeedbackEntity = hasFeedbackEntity;
    }

    public boolean isHasAppealOwner() {
        return hasAppealOwner;
    }

    public void setHasAppealOwner(boolean hasAppealOwner) {
        this.hasAppealOwner = hasAppealOwner;
    }

    public boolean isHasAppealEntity() {
        return hasAppealEntity;
    }

    public void setHasAppealEntity(boolean hasAppealEntity) {
        this.hasAppealEntity = hasAppealEntity;
    }
}
