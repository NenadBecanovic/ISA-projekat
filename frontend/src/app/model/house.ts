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
  ava: number;
  images: Image[] = new Array();
  ownerId: number;
  numberOfReviews: number = 0;
  avarageGrade: number = 0;

  constructor(id: number, name: String, addressDTO: Address, promoDescription: String, behaviourRules: String, pricePerDay: number,
              cancalletionFree: boolean, cancalletionFee: number, rooms: Room[], additionalServices: AdditionalService[], grade: number, ownerId: number) {
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
    this.ava = grade;
    this.ownerId = ownerId;
  }
}


