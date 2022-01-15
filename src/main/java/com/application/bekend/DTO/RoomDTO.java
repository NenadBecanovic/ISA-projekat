package com.application.bekend.DTO;

public class RoomDTO {

    private Long houseId;
    private Long id;
    private int numberOfBeds;

    public RoomDTO(Long id, int numberOfBeds)
    {
        this.id = id;
        this.numberOfBeds = numberOfBeds;
    }

    public RoomDTO(Long id, int numberOfBeds, Long houseId)
    {
        this.houseId = houseId;
        this.id = id;
        this.numberOfBeds = numberOfBeds;
    }

    public RoomDTO(){}

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

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
}
