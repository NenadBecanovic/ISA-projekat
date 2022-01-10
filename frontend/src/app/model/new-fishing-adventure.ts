import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { Image } from "./image";

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
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  image: String | ArrayBuffer;
  instructorId: number=0;

  constructor(name: String, address: Address, promoDescription: String, capacity: number, fishingEquipment: String, behaviourRules: String, pricePerHour: number, isCancellationFree: boolean, cancelationFee: number, image:String | ArrayBuffer){
    this.name = name;
    this.address = address;
    this.promoDescription = promoDescription;
    this.capacity = capacity;
    this.fishingEquipment = fishingEquipment;
    this.behaviourRules = behaviourRules;
    this.pricePerHour = pricePerHour;
    this.isCancellationFree = isCancellationFree;
    this.cancellationFee = cancelationFee;
    this.image = image;
  }

}
