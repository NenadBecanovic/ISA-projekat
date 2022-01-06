package com.application.bekend.DTO;

public class AddressDTO {

    private Long id;
    private String street;
    private String city;
    private String state;
    private float longitude;
    private float latitude;
    private int postalCode;

    public AddressDTO(Long id, String street, String city, String state, float longitude, float latitude, int postalCode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
        this.postalCode = postalCode;
    }

    public AddressDTO() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getStreet() {
        return street;
    }

    public void setStreet() {
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

    public int getPostalCode() { return postalCode; }

    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }
}
