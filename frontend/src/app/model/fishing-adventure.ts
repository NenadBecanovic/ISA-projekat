import { makeStateKey } from "@angular/platform-browser";
import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { FishingAdventureInstructorDTO } from "./fishing-adventure-instructorDTO";
import { MyUser } from "./my-user";
import {Image} from "./image";

export class FishingAdventure {

  id: number;
  name: String;
  address: Address;
  promoDescription: String;
  capacity: number;
  fishingEquipment: String;
  behaviourRules: String;
  pricePerHour: number;
  cancellationFree: boolean;
  cancellationFee: number;
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  instructorId : number = 0;
  images: Image[] = new Array();

  constructor(id: number,name: String, address: Address, promoDescription: String, capacity: number, fishingEquipment: String, behaviourRules: String, pricePerHour: number, isCancellationFree: boolean, cancelationFee: number){
    this.id = id;
    this.name = name;
    this.address = address;
    this.promoDescription = promoDescription;
    this.capacity = capacity;
    this.fishingEquipment = fishingEquipment;
    this.behaviourRules = behaviourRules;
    this.pricePerHour = pricePerHour;
    this.cancellationFree = isCancellationFree;
    this.cancellationFee = cancelationFee;
  }

}
