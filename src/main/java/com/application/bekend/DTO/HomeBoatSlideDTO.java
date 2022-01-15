package com.application.bekend.DTO;

import java.util.List;

public class HomeBoatSlideDTO {

    private List<BoatDTO> boats;

    public HomeBoatSlideDTO(List<BoatDTO> boats) {
        this.boats = boats;
    }

    public HomeBoatSlideDTO() {
    }

    public List<BoatDTO> getBoats() {
        return boats;
    }

    public void setBoats(List<BoatDTO> boats) {
        this.boats = boats;
    }
}
