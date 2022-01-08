package com.application.bekend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private Float longitude;
    private Float latitude;
    private Integer postalCode;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private Set<MyUser> users= new HashSet<MyUser>();

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private Set<Boat> boats= new HashSet<Boat>();

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private Set<House> houses= new HashSet<House>();

    public Address(Long id, String street, String city, String state, Float longitude, Float latitude, Integer postalCode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Set<MyUser> getUsers() {
        return users;
    }

    public void setUsers(Set<MyUser> users) {
        this.users = users;
    }

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }

    public int getPostalCode() { return postalCode; }

    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }
}
