import {House} from "./house";

export class HouseReservation {

  startDate: Date;
  endDate: Date;
  maxGuests: number;
  additionalServices: String;
  price: number;
  isAvailable: boolean;
  //private Set<MyUser> guests = new HashSet<MyUser>();
  house: House;

  constructor(startDate: Date, endDate: Date, maxGuests: number, additionalServices: String, price: number, isAvailable: boolean, house: House) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.maxGuests = maxGuests;
    this.additionalServices = additionalServices;
    this.price = price;
    this.isAvailable = isAvailable;
    this.house = house;
  }

}
