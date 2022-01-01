import {House} from "./house";

export class Room {

   numberOfBeds: number;

  constructor(numberOfBeds: number, house: House) {
    this.numberOfBeds = numberOfBeds;
  }

}
