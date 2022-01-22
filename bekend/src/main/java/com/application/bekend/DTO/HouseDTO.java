package com.application.bekend.DTO;

import java.util.HashSet;
import java.util.Set;

public class HouseDTO {

    private Long id;
    private String name;
    private Set<RoomDTO> rooms;
    private AddressDTO address;
    private String promoDescription;
    //    private Set<HouseReservation> courses = new HashSet<HouseReservation>();
    private String behaviourRules;
    private float pricePerDay;
    private Set<AdditionalServicesDTO> services;
    private boolean isCancalletionFree;
    private int cancalletionFee;
    private double grade;
    private Set<ImageDTO> images;
    private Long ownerId;
    private int numberOfReviews;

    public HouseDTO(Long id, String name, AddressDTO addressDTO, String promoDescription, String behaviourRules, float pricePerDay,
                    boolean isCancalletionFree, int cancalletionFee) {
        this.id = id;
        this.name = name;
        this.address = addressDTO;
        this.promoDescription = promoDescription;
        this.behaviourRules = behaviourRules;
        this.pricePerDay = pricePerDay;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
    }

    public HouseDTO(Long id, String name, AddressDTO addressDTO, String promoDescription, String behaviourRules, float pricePerDay,
                    boolean isCancalletionFree, int cancalletionFee, Set<RoomDTO> rooms, Set<AdditionalServicesDTO> services) {
        this.id = id;
        this.name = name;
        this.address = addressDTO;
        this.promoDescription = promoDescription;
        this.behaviourRules = behaviourRules;
        this.pricePerDay = pricePerDay;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
        this.rooms = rooms;
        this.services = services;
    }

    public HouseDTO() {
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public String getBehaviourRules() {
        return behaviourRules;
    }

    public void setBehaviourRules(String behaviourRules) {
        this.behaviourRules = behaviourRules;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isCancalletionFree() {
        return isCancalletionFree;
    }

    public void setCancalletionFree(boolean cancalletionFree) {
        isCancalletionFree = cancalletionFree;
    }

    public int getCancalletionFee() {
        return cancalletionFee;
    }

    public void setCancalletionFee(int cancalletionFee) {
        this.cancalletionFee = cancalletionFee;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Set<ImageDTO> getImages() {
        return images;
    }

    public void setImages(Set<ImageDTO> images) {
        this.images = images;
    }
  
    public Set<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public Set<AdditionalServicesDTO> getServices() {
        return services;
    }

    public void setServices(Set<AdditionalServicesDTO> services) {
        this.services = services;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }
}
