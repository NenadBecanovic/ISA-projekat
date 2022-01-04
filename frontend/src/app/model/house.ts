import {Address} from "./address";
import {Image} from "./image";
import {Room} from "./room";
import {AdditionalService} from "./additional-service";

export class House {

  id: number;
  name: String;
  address: Address;
  rooms: Room[];                 //
  // courses: Array<HouseReservation>;   //
  promoDescription: String;
  behaviourRules: String;
  pricePerDay: number;
  services: AdditionalService[];
  cancalletionFree: boolean;
  cancalletionFee: number;
  // images: Array<Image>;
  //images[]: Image;

  constructor(id: number, name: String, addressDTO: Address, promoDescription: String, behaviourRules: String, pricePerDay: number,
              cancalletionFree: boolean, cancalletionFee: number, rooms: Room[], additionalServices: AdditionalService[]) {
    this.id = id;
    this.name = name;
    this.address = addressDTO;
    this.rooms = rooms;
    this.promoDescription = promoDescription;
    this.behaviourRules = behaviourRules;
    this.pricePerDay = pricePerDay;
    this.services = additionalServices;
    this.cancalletionFree = cancalletionFree;
    this.cancalletionFee = cancalletionFee;
    // this.courses = courses;
    // this.images = images;
  }

}


