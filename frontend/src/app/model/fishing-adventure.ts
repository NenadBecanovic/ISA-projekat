import { makeStateKey } from "@angular/platform-browser";
import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { MyUser } from "./my-user";

export class FishingAdventure {

  id: number;
  name: String;
  address: Address;
  promoDescription: String;
  capacity: number;
  fishingEquipment: String;
  behaviourRules: String;
  pricePerHour: number;
  services: Array<AdditionalService>;
  isCancellationFree: boolean;
  cancellationFee: number;
  instructor: MyUser;

  constructor(id: number,name: String, address: Address, promoDescription: String, capacity: number, fishingEquipment: String, behaviourRules: String, pricePerHour: number, services: Array<AdditionalService>, isCancellationFree: boolean, cancelationFee: number, instructor: MyUser){
    this.id = id;
    this.name = name;
    this.address = address;
    this.promoDescription = promoDescription;
    this.capacity = capacity;
    this.fishingEquipment = fishingEquipment;
    this.behaviourRules = behaviourRules;
    this.pricePerHour = pricePerHour;
    this.services = services;
    this.isCancellationFree = isCancellationFree;
    this.cancellationFee = cancelationFee;
    this.instructor = instructor;
  }

}
