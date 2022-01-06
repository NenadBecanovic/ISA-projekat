package com.application.bekend.DTO;

import java.util.List;

public class HomeAdventureSlideDTO {

    private List<FishingAdventureDTO> adventures;

    public HomeAdventureSlideDTO(List<FishingAdventureDTO> adventures) {
        this.adventures = adventures;
    }

    public HomeAdventureSlideDTO() {
    }

    public List<FishingAdventureDTO> getAdventures() {
        return adventures;
    }

    public void setAdventures(List<FishingAdventureDTO> adventures) {
        this.adventures = adventures;
    }
}
