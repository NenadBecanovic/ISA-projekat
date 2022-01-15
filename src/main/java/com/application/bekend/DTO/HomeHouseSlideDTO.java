package com.application.bekend.DTO;

import java.util.List;

public class HomeHouseSlideDTO {

    private List<HouseDTO> houses;

    public HomeHouseSlideDTO(List<HouseDTO> houses) {
        this.houses = houses;
    }

    public HomeHouseSlideDTO() {
    }

    public List<HouseDTO> getHouses() {
        return houses;
    }

    public void setHouses(List<HouseDTO> houses) {
        this.houses = houses;
    }
}
