import {Address} from "./address";
import {Image} from "./image";

export class House {

  id: number;
  name: String;
  address: Address;
  // rooms: Array<Room>;                 //
  // courses: Array<HouseReservation>;   //
  promoDescription: String;
  behaviourRules: String;
  pricePerDay: number;
  // services: Array<AdditionalService>; //
  isCancalletionFree: boolean;
  cancalletionFee: number;
  // images: Array<Image>;
  //images[]: Image;

  constructor(id: number, name: String, addressDTO: Address, promoDescription: String, behaviourRules: String, pricePerDay: number,
              isCancalletionFree: boolean, cancalletionFee: number) {
    this.id = id;
    this.name = name;
    this.address = addressDTO;
    // this.rooms = rooms;
    this.promoDescription = promoDescription;
    this.behaviourRules = behaviourRules;
    this.pricePerDay = pricePerDay;
    // this.services = services;
    this.isCancalletionFree = isCancalletionFree;
    this.cancalletionFee = cancalletionFee;
    // this.courses = courses;
    // this.images = images;
  }

}


