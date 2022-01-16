package com.application.bekend.DTO;

public class ReservationCheckDTO {

    private Long startMilis;
    private Long endMilis;
    private int maxGuest;
    private String email;

    public ReservationCheckDTO(Long startMilis, Long endMilis, int maxGuest) {
        this.startMilis = startMilis;
        this.endMilis = endMilis;
        this.maxGuest = maxGuest;
    }

    public ReservationCheckDTO() {
    }

    public Long getStartMilis() {
        return startMilis;
    }

    public void setStartMilis(Long startMilis) {
        this.startMilis = startMilis;
    }

    public Long getEndMilis() {
        return endMilis;
    }

    public void setEndMilis(Long endMilis) {
        this.endMilis = endMilis;
    }

    public int getMaxGuest() {
        return maxGuest;
    }

    public void setMaxGuest(int maxGuest) {
        this.maxGuest = maxGuest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
