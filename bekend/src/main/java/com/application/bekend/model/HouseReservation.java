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

    @ManyToMany(mappedBy = "houseReservations")
    private Set<MyUser> guests = new HashSet<MyUser>();

    // vodeca strana ManyToMany veze, sto znaci ako nesto uklonimo sa strane House uklonice se i sa druge strane ManyToMany veze
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "house_reservation_table", joinColumns = @JoinColumn(name = "house_appointment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "house_id", referencedColumnName = "id"))
    private House house;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade =
//                    {
//                            CascadeType.DETACH,
//                            CascadeType.MERGE,
//                            CascadeType.REFRESH,
//                            CascadeType.PERSIST
//                    },
//            targetEntity = AdditionalServices.class)
//    @JoinTable(name = "additional_services_house_reservation",
//            inverseJoinColumns = @JoinColumn(name = "additional_services_id",
//                    nullable = false,
//                    updatable = false),
//            joinColumns = @JoinColumn(name = "house_reservation_id",
//                    nullable = false,
//                    updatable = false),
//            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
//            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
//    private  Set<AdditionalServices> additionalServices = new HashSet<>();

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

    public Set<MyUser> getGuests() {
        return guests;
    }

    public void setGuests(Set<MyUser> guests) {
        this.guests = guests;
    }

    public Set<AdditionalServices> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalServices> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void addAdditionalService(AdditionalServices additionalServices){
        this.additionalServices.add(additionalServices);
    }
}
