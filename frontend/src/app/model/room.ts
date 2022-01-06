import {House} from "./house";

export class Room {

  public houseId: number = 0;
  id: number;
  numberOfBeds: number;

  constructor(id: number, numberOfBeds: number, house: House) {
    this.id = id;
    this.numberOfBeds = numberOfBeds;
  }

}
