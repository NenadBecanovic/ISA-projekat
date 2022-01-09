package com.application.bekend.DTO;

public class AppealDTO {

    private Long reservationId;
    private String review;
    private Long ownerId;
    private Long senderId;
    private boolean hasHouse;
    private boolean hasHouseOwner;
    private boolean hasBoat;
    private boolean hasBoatOwner;
    private boolean hasInstructor;

    public AppealDTO(Long reservationId, String review, Long ownerId, boolean hasHouse, boolean hasHouseOwner, boolean hasBoat, boolean hasBoatOwner, boolean hasInstructor) {
        this.reservationId = reservationId;
        this.review = review;
        this.ownerId = ownerId;
        this.hasHouse = hasHouse;
        this.hasHouseOwner = hasHouseOwner;
        this.hasBoat = hasBoat;
        this.hasBoatOwner = hasBoatOwner;
        this.hasInstructor = hasInstructor;
    }

    public AppealDTO() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isHasHouse() {
        return hasHouse;
    }

    public void setHasHouse(boolean hasHouse) {
        this.hasHouse = hasHouse;
    }

    public boolean isHasHouseOwner() {
        return hasHouseOwner;
    }

    public void setHasHouseOwner(boolean hasHouseOwner) {
        this.hasHouseOwner = hasHouseOwner;
    }

    public boolean isHasBoat() {
        return hasBoat;
    }

    public void setHasBoat(boolean hasBoat) {
        this.hasBoat = hasBoat;
    }

    public boolean isHasBoatOwner() {
        return hasBoatOwner;
    }

    public void setHasBoatOwner(boolean hasBoatOwner) {
        this.hasBoatOwner = hasBoatOwner;
    }

    public boolean isHasInstructor() {
        return hasInstructor;
    }

    public void setHasInstructor(boolean hasInstructor) {
        this.hasInstructor = hasInstructor;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
}

