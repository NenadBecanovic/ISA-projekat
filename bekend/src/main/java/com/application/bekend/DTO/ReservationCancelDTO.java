package com.application.bekend.DTO;

public class ReservationCancelDTO {

    private Long reservationId;
    private Long guestId;
    private boolean isHouse;
    private boolean isBoat;
    private boolean isAdventure;

    public ReservationCancelDTO(Long reservationId, Long guestId, boolean isHouse, boolean isBoat, boolean isAdventure) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.isHouse = isHouse;
        this.isBoat = isBoat;
        this.isAdventure = isAdventure;
    }

    public ReservationCancelDTO() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public boolean getIsHouse() {
        return isHouse;
    }

    public void setIsHouse(boolean house) {
        isHouse = house;
    }

    public boolean getIsBoat() {
        return isBoat;
    }

    public void setIsBoat(boolean boat) {
        isBoat = boat;
    }

    public boolean getIsAdventure() {
        return isAdventure;
    }

    public void setIsAdventure(boolean adventure) {
        isAdventure = adventure;
    }
}
