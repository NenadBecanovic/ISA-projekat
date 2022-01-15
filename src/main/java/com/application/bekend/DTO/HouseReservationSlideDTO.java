package com.application.bekend.DTO;

import com.application.bekend.model.HouseReservation;

import java.util.List;

public class HouseReservationSlideDTO {

    private List<HouseReservationDTO> houseReservationDTOList;

    public HouseReservationSlideDTO(List<HouseReservationDTO> houseReservationDTOList) {
        this.houseReservationDTOList = houseReservationDTOList;
    }

    public HouseReservationSlideDTO(){}

    public List<HouseReservationDTO> getHouseReservationDTOList() {
        return houseReservationDTOList;
    }

    public void setHouseReservationDTOList(List<HouseReservationDTO> houseReservationDTOList) {
        this.houseReservationDTOList = houseReservationDTOList;
    }
}
