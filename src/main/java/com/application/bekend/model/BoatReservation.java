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
    private boolean availabilityPeriod = false;
    private boolean isAction = false;
    private Boolean isCancelled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private MyUser guest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_id")
    private Boat boat;

    @ManyToMany(mappedBy = "boatReservationsServices")
    private Set<AdditionalServices> additionalServices = new HashSet<>();

    @OneToOne(mappedBy = "boatReservation")
    private Report report;

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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }
}
