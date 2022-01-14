package com.application.bekend.DTO;

public class FeedbackDTO {

    private Long reservationId;
    private int grade;
    private String review;
    private Long ownerId;
    private boolean hasHouse;
    private boolean hasHouseOwner;
    private boolean hasBoat;
    private boolean hasBoatOwner;
    private boolean hasInstructor;
    private boolean isApproved;
    
    public FeedbackDTO(Long reservationId, int grade, String review) {
        this.reservationId = reservationId;
        this.grade = grade;
        this.review = review;
    }

    public FeedbackDTO() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    public boolean getHasHouse() {
        return hasHouse;
    }

    public void setHasHouse(boolean hasHouse) {
        this.hasHouse = hasHouse;
    }

    public boolean getHasHouseOwner() {
        return hasHouseOwner;
    }

    public void setHasHouseOwner(boolean hasHouseOwner) {
        this.hasHouseOwner = hasHouseOwner;
    }

    public boolean getHasBoat() {
        return hasBoat;
    }

    public void setHasBoat(boolean hasBoat) {
        this.hasBoat = hasBoat;
    }

    public boolean getHasBoatOwner() {
        return hasBoatOwner;
    }

    public void setHasBoatOwner(boolean hasBoatOwner) {
        this.hasBoatOwner = hasBoatOwner;
    }

    public boolean getHasInstructor() {
        return hasInstructor;
    }

    public void setHasInstructor(boolean hasInstructor) {
        this.hasInstructor = hasInstructor;
    }

	public boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
}
