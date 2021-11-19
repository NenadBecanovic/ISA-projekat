package com.application.bekend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AdventureReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private int maxGuests;
    private String additionalServices;
    private float price;
    private boolean isAvailable;

    @ManyToMany(mappedBy = "adventureReservations")
    private Set<MyUser> guests = new HashSet<MyUser>();

    @ManyToMany
    @JoinTable(name = "adventure_reservation_table", joinColumns = @JoinColumn(name = "adventure_appointment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "adventure_id", referencedColumnName = "id"))
    private Set<FishingAdventure> fishingAdventures = new HashSet<FishingAdventure>();

    public AdventureReservation(Long id, Date startDate, Date endDate, int maxGuests, String additionalServices, float price, boolean isAvailable, Set<FishingAdventure> fishingAdventures) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.additionalServices = additionalServices;
        this.price = price;
        this.isAvailable = isAvailable;
        this.fishingAdventures = fishingAdventures;
    }

    public AdventureReservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public Set<FishingAdventure> getFishingAdventures() {
        return fishingAdventures;
    }

    public void setFishingAdventures(Set<FishingAdventure> fishingAdventures) {
        this.fishingAdventures = fishingAdventures;
    }
}
