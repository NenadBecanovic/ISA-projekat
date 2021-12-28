import {Address} from "./address";
import {Room} from "./room";
import {AdditionalService} from "./additional-service";
import {HouseReservation} from "./house-reservation";

export class House {

  name: String;
  address: Address;
  rooms: Array<Room>;
  courses: Array<HouseReservation>;
  promoDescription: String;
  behaviourRules: String;
  pricePerDay: number;
  services: Array<AdditionalService>;
  isCancalletionFree: boolean;
  cancalletionFee: number;

  constructor(name: String, address: Address, promoDescription: String, behaviourRules: String, pricePerDay: number,
              isCancalletionFree: boolean, cancalletionFee: number, rooms: Array<Room>, services: Array<AdditionalService>, courses: Array<HouseReservation>) {
    this.name = name;
    this.address = address;
    this.rooms = rooms;
    this.promoDescription = promoDescription;
    this.behaviourRules = behaviourRules;
    this.pricePerDay = pricePerDay;
    this.services = services;
    this.isCancalletionFree = isCancalletionFree;
    this.cancalletionFee = cancalletionFee;
    this.courses = courses;
  }

}


