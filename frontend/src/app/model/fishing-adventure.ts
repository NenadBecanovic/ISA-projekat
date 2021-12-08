import { makeStateKey } from "@angular/platform-browser";
import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { MyUser } from "./my-user";

export class FishingAdventure {

  name: String;
  address: Address;
  promoDescription: String;
  capacity: number;
  fishingEquipment: String;
  behaviourRules: String;
  pricePerDay: number;
  services: Array<AdditionalService>;
  isCancellationFree: boolean;
  cancellationFee: number;
  instructor: MyUser;

  constructor(name: String, address: Address, promoDescription: String, capacity: number, fishingEquipment: String, behaviourRules: String, pricePerDay: number, services: Array<AdditionalService>, isCancellationFree: boolean, cancelationFee: number, instructor: MyUser){
    this.name = name;
    this.address = address;
    this.promoDescription = promoDescription;
    this.capacity = capacity;
    this.fishingEquipment = fishingEquipment;
    this.behaviourRules = behaviourRules;
    this.pricePerDay = pricePerDay;
    this.services = services;
    this.isCancellationFree = isCancellationFree;
    this.cancellationFee = cancelationFee;
    this.instructor = instructor;
  }

}
