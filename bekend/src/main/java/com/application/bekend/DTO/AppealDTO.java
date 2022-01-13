package com.application.bekend.DTO;

public class AppealDTO {

	private Long id;
    private Long reservationId;
    private String review;
    private Long ownerId;
    private Long senderId;
    private boolean hasHouse;
    private boolean hasHouseOwner;
    private boolean hasBoat;
    private boolean hasBoatOwner;
    private boolean hasInstructor;
    private boolean isAnswered = false;
    private UserInfoDTO guest = new UserInfoDTO();
    private UserInfoDTO owner = new UserInfoDTO();
    public AppealDTO(Long id, String review, boolean hasHouse, boolean hasHouseOwner, boolean hasBoat, boolean hasBoatOwner, boolean hasInstructor) {
    	this.id = id;
        this.review = review;
        this.hasHouse = hasHouse;
        this.hasHouseOwner = hasHouseOwner;
        this.hasBoat = hasBoat;
        this.hasBoatOwner = hasBoatOwner;
        this.hasInstructor = hasInstructor;
    }

    public AppealDTO() {
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public UserInfoDTO getGuest() {
		return guest;
	}

	public void setGuest(UserInfoDTO guest) {
		this.guest = guest;
	}

	public UserInfoDTO getOwner() {
		return owner;
	}

	public void setOwner(UserInfoDTO owner) {
		this.owner = owner;
	}
}

