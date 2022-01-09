package com.application.bekend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BoatReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private int maxGuests;
    private float price;
    private boolean isAvailable;
    private Boolean hasFeedbackOwner = false;
    private Boolean hasFeedbackEntity = false;
    private Boolean hasAppealOwner = false;
    private Boolean hasAppealEntity = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private MyUser guest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "boat_reservation_table", joinColumns = @JoinColumn(name = "boat_appointment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "boat_id", referencedColumnName = "id"))
    private Boat boat;

    @ManyToMany(mappedBy = "boatReservationsServices")
    private Set<AdditionalServices> additionalServices = new HashSet<>();

    public BoatReservation(Long id, Date startDate, Date endDate, int maxGuests, float price, boolean isAvailable,Boat boat) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.price = price;
        this.isAvailable = isAvailable;
        this.boat = boat;
    }

    public BoatReservation() {
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

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public MyUser getGuest() {
        return guest;
    }

    public void setGuest(MyUser guest) {
        this.guest = guest;
    }

    public Set<AdditionalServices> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalServices> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public Boolean getHasFeedbackOwner() {
        return hasFeedbackOwner;
    }

    public void setHasFeedbackOwner(Boolean hasFeedbackOwner) {
        this.hasFeedbackOwner = hasFeedbackOwner;
    }

    public Boolean getHasFeedbackEntity() {
        return hasFeedbackEntity;
    }

    public void setHasFeedbackEntity(Boolean hasFeedbackEntity) {
        this.hasFeedbackEntity = hasFeedbackEntity;
    }

    public Boolean getHasAppealOwner() {
        return hasAppealOwner;
    }

    public void setHasAppealOwner(Boolean hasAppealOwner) {
        this.hasAppealOwner = hasAppealOwner;
    }

    public Boolean getHasAppealEntity() {
        return hasAppealEntity;
    }

    public void setHasAppealEntity(Boolean hasAppealEntity) {
        this.hasAppealEntity = hasAppealEntity;
    }
}
