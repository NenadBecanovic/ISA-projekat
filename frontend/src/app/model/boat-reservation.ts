import {Boat} from "./boat";

export class BoatReservation {

  id: number;
  startDate: Date;
  endDate: Date;
  maxGuests: number;
  additionalServices: String;
  price: number;
  isAvailable: boolean;

  constructor(id: number, startDate: Date, endDate: Date, maxGuests: number, additionalServices: String, price: number, isAvailable: boolean) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.maxGuests = maxGuests;
    this.additionalServices = additionalServices;
    this.price = price;
    this.isAvailable = isAvailable;
  }
}
