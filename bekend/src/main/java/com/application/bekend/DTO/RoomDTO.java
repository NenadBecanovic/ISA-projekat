package com.application.bekend.DTO;

public class RoomDTO {

    private Long id;
    private int numberOfBeds;

    public RoomDTO(Long id, int numberOfBeds)
    {
        this.id = id;
        this.numberOfBeds = numberOfBeds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
}
