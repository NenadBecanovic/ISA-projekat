package com.application.bekend.DTO;

public class FishingAdventureDTO {

    private Long id;
    private String name;
    private AddressDTO address;
    private String promoDescription;
    private int capacity;
    private String fishingEquipment;
    private String behaviourRules;
    private float pricePerDay;
    private boolean isCancalletionFree;
    private int cancalletionFee;

    public FishingAdventureDTO(Long id, String name, AddressDTO address, String promoDescription, int capacity, String fishingEquipment,
                               String behaviourRules, float pricePerDay, boolean isCancalletionFree, int cancalletionFee) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.fishingEquipment = fishingEquipment;
        this.behaviourRules = behaviourRules;
        this.pricePerDay = pricePerDay;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
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
}
