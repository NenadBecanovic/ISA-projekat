import {HouseReservation} from "./house-reservation";

export class HouseReservationSlide {

  houseReservationDTOList: HouseReservation[];

  constructor(houseReservationDTOList: HouseReservation[]) {
    this.houseReservationDTOList = houseReservationDTOList;
  }
}
