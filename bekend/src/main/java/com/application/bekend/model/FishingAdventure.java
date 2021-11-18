package com.application.bekend.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FishingAdventure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    private String promoDescription;
    private int capacity;
    private String fishingEquipment;

    //    private Set<String> images ;
    @ManyToMany(mappedBy = "fishingAdventures")
    private Set<AdventureReservation> adventureReservations = new HashSet<AdventureReservation>();
    private String behaviourRules;
    private float pricePerDay;
    @ManyToMany(mappedBy = "fishingAdventures")
    private Set<AdditionalServices> services = new HashSet<AdditionalServices>();
    private boolean isCancalletionFree;
    private int cancalletionFee;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private MyUser instructor;

    public FishingAdventure(Long id, String name, Address address, String promoDescription, int capacity, String fishingEquipment, Set<AdventureReservation> adventureReservations, String behaviourRules, float pricePerDay, Set<AdditionalServices> services, boolean isCancalletionFree, int cancalletionFee) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.fishingEquipment = fishingEquipment;
        this.adventureReservations = adventureReservations;
        this.behaviourRules = behaviourRules;
        this.pricePerDay = pricePerDay;
        this.services = services;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
    }

    public FishingAdventure() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    public Set<AdventureReservation> getAdventureReservations() {
        return adventureReservations;
    }

    public void setAdventureReservations(Set<AdventureReservation> adventureReservations) {
        this.adventureReservations = adventureReservations;
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

    public Set<AdditionalServices> getServices() {
        return services;
    }

    public void setServices(Set<AdditionalServices> services) {
        this.services = services;
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
