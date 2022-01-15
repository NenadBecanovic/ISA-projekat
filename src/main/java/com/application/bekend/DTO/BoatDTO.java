package com.application.bekend.DTO;

import java.util.Set;

public class BoatDTO {
    private Long id;
    private String name;
    private String type;
    private float length;
    private int engineNumber;
    private float enginePower;
    private float maxSpeed;
    private String promoDescription;
    private int capacity;
    private String behaviourRules;
    private String fishingEquipment;
    private float pricePerDay;
    private boolean isCancalletionFree;
    private int cancalletionFee;
    private AddressDTO address;
    private double grade;
    private Set<ImageDTO> images;
    private NavigationEquipmentDTO navigationEquipmentDTO;
    private Set<AdditionalServicesDTO> services;
    private Long ownerId;

    public BoatDTO(Long id, String name, String type, float length, int engineNumber, float enginePower, float maxSpeed,
                   String promoDescription, int capacity, String behaviourRules, String fishingEquipment, float pricePerDay,
                   boolean isCancalletionFree, int cancalletionFee, AddressDTO addressDTO,
                   NavigationEquipmentDTO navigationEquipmentDTO) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.length = length;
        this.engineNumber = engineNumber;
        this.enginePower = enginePower;
        this.maxSpeed = maxSpeed;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.behaviourRules = behaviourRules;
        this.fishingEquipment = fishingEquipment;
        this.pricePerDay = pricePerDay;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
        this.address = addressDTO;
        this.navigationEquipmentDTO = navigationEquipmentDTO;
    }

    public BoatDTO(Long id, String name, String type, float length, int engineNumber, float enginePower, float maxSpeed,
                   String promoDescription, int capacity, String behaviourRules, String fishingEquipment, float pricePerDay,
                   boolean isCancalletionFree, int cancalletionFee, AddressDTO addressDTO,
                   NavigationEquipmentDTO navigationEquipmentDTO, Set<AdditionalServicesDTO> services) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.length = length;
        this.engineNumber = engineNumber;
        this.enginePower = enginePower;
        this.maxSpeed = maxSpeed;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.behaviourRules = behaviourRules;
        this.fishingEquipment = fishingEquipment;
        this.pricePerDay = pricePerDay;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
        this.address = addressDTO;
        this.navigationEquipmentDTO = navigationEquipmentDTO;
        this.services = services;
    }

    public BoatDTO() {}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(int engineNumber) {
        this.engineNumber = engineNumber;
    }

    public float getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(float enginePower) {
        this.enginePower = enginePower;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBehaviourRules() {
        return behaviourRules;
    }

    public void setBehaviourRules(String behaviourRules) {
        this.behaviourRules = behaviourRules;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
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

    public AddressDTO getAddress() { return address; }

    public void setAddress(AddressDTO address) { this.address = address; }

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

    public Set<AdditionalServicesDTO> getServices() {
        return services;
    }

    public void setServices(Set<AdditionalServicesDTO> services) {
        this.services = services;
    }

    public NavigationEquipmentDTO getNavigationEquipmentDTO() {
        return navigationEquipmentDTO;
    }

    public void setNavigationEquipmentDTO(NavigationEquipmentDTO navigationEquipmentDTO) {
        this.navigationEquipmentDTO = navigationEquipmentDTO;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
