package com.application.bekend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class HouseReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    private int maxGuests;
    private float price;
    private boolean isAvailable;
    private boolean availabilityPeriod = false;
    private boolean isAction = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private MyUser guest;

    // vodeca strana ManyToMany veze, sto znaci ako nesto uklonimo sa strane House uklonice se i sa druge strane ManyToMany veze
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "house_reservation_table", joinColumns = @JoinColumn(name = "house_appointment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "house_id", referencedColumnName = "id"))
    private House house;

    @ManyToMany(mappedBy = "houseReservationsServices")
    private Set<AdditionalServices> additionalServices = new HashSet<>();

    public HouseReservation(Long id, Date startDate, Date endDate, int maxGuests, float price, boolean isAvailable, House house) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.price = price;
        this.isAvailable = isAvailable;
        this.house = house;
    }

    public HouseReservation() {
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

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
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

    public boolean isAvailabilityPeriod() {
        return availabilityPeriod;
    }

    public void setAvailabilityPeriod(boolean availabilityPeriod) {
        this.availabilityPeriod = availabilityPeriod;
    }

    public void setAdditionalServices(Set<AdditionalServices> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void addAdditionalService(AdditionalServices additionalServices){
        this.additionalServices.add(additionalServices);
    }

    public boolean isAction() {
        return isAction;
    }

    public void setAction(boolean action) {
        isAction = action;
    }
}
