import {Address} from "./address";
import {Room} from "./room";
import {AdditionalService} from "./additional-service";
import {HouseReservation} from "./house-reservation";
import {Image} from "./image";


export class House {

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
  images: Array<Image>;
//images[]: Image;

  constructor(name: String, addressDTO: Address, promoDescription: String, behaviourRules: String, pricePerDay: number,
              isCancalletionFree: boolean, cancalletionFee: number, images: Array<Image>) {
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
    this.images = images;
  }

}


