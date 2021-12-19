import {House} from "./house";

export class Room {

   numberOfBeds: number;
   house: House;

  constructor(numberOfBeds: number, house: House) {
    this.numberOfBeds = numberOfBeds;
    this.house = house;
  }

}
