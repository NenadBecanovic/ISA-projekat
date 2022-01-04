import { makeStateKey } from "@angular/platform-browser";
import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { FishingAdventureInstructorDTO } from "./fishing-adventure-instructorDTO";

export class NewFishingAdventure {

  name: String;
  address: Address;
  promoDescription: String;
  capacity: number;
  fishingEquipment: String;
  behaviourRules: String;
  pricePerHour: number;
  isCancellationFree: boolean;
  cancellationFee: number;

  constructor(name: String, address: Address, promoDescription: String, capacity: number, fishingEquipment: String, behaviourRules: String, pricePerHour: number, isCancellationFree: boolean, cancelationFee: number){
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
