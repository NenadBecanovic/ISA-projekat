import {Boat} from "./boat";

export class BoatReservation {

  startDate: Date;
  endDate: Date;
  maxGuests: number;
  additionalServices: String;
  price: number;
  isAvailable: boolean;
  //private Set<MyUser> guests = new HashSet<MyUser>();
  boat: Boat;

  constructor(startDate: Date, endDate: Date, maxGuests: number, additionalServices: String, price: number, isAvailable: boolean, boat: Boat) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.maxGuests = maxGuests;
    this.additionalServices = additionalServices;
    this.price = price;
    this.isAvailable = isAvailable;
    this.boat = boat;
  }
}
