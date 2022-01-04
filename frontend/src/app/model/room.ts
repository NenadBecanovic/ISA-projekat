import {House} from "./house";

export class Room {

  id: number;
  numberOfBeds: number;

  constructor(id: number, numberOfBeds: number, house: House) {
    this.id = id;
    this.numberOfBeds = numberOfBeds;
  }

}
