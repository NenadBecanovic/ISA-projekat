import { makeStateKey } from "@angular/platform-browser";
import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { FishingAdventureInstructorDTO } from "./fishing-adventure-instructorDTO";
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
  isCancellationFree: boolean;
  cancellationFee: number;

  constructor(id: number,name: String, address: Address, promoDescription: String, capacity: number, fishingEquipment: String, behaviourRules: String, pricePerHour: number, isCancellationFree: boolean, cancelationFee: number){
    this.id = id;
    this.name = name;
    this.address = address;
    this.promoDescription = promoDescription;
    this.capacity = capacity;
    this.fishingEquipment = fishingEquipment;
    this.behaviourRules = behaviourRules;
    this.pricePerHour = pricePerHour;
    this.isCancellationFree = isCancellationFree;
    this.cancellationFee = cancelationFee;
  }

}
