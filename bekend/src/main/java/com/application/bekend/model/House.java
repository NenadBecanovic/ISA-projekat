package com.application.bekend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<Room>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    private String promoDescription;

    //    private Set<String> images ;
    @OneToMany(mappedBy = "house")
    private Set<HouseReservation> courses = new HashSet<HouseReservation>();
    private String behaviourRules;
    private float pricePerDay;

    @ManyToMany(mappedBy = "houses")
    private Set<AdditionalServices> services = new HashSet<AdditionalServices>();
    private boolean isCancalletionFree;
    private int cancalletionFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private MyUser owner;

    public House(Long id, String name, Set<Room> rooms, Address address, String promoDescription, Set<HouseReservation> courses, String behaviourRules, float pricePerDay, Set<AdditionalServices> services, boolean isCancalletionFree, int cancalletionFee) {
        this.id = id;
        this.name = name;
        this.rooms = rooms;
        this.address = address;
        this.promoDescription = promoDescription;
        this.courses = courses;
        this.behaviourRules = behaviourRules;
        this.pricePerDay = pricePerDay;
        this.services = services;
        this.isCancalletionFree = isCancalletionFree;
        this.cancalletionFee = cancalletionFee;
    }

    public House() {
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

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
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

    public Set<HouseReservation> getCourses() {
        return courses;
    }

    public void setCourses(Set<HouseReservation> courses) {
        this.courses = courses;
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
