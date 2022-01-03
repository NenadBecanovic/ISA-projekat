package com.application.bekend.DTO;

import java.util.List;

public class BoatReservationSlideDTO {

    private List<BoatReservationDTO> boatReservationDTOList;

    public BoatReservationSlideDTO(List<BoatReservationDTO> boatReservationDTOList) {
        this.boatReservationDTOList = boatReservationDTOList;
    }

    public List<BoatReservationDTO> getBoatReservationDTOList() {
        return boatReservationDTOList;
    }

    public void setBoatReservationDTOList(List<BoatReservationDTO> boatReservationDTOList) {
        this.boatReservationDTOList = boatReservationDTOList;
    }
}
