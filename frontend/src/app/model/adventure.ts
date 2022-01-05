import {Address} from "./address";
import {Image} from "./image";

export class Adventure {


  id:number
  name: String;
  address: Address;
  promoDescription: String;
  capacity: number;
  fishingEquipment: String;
  behaviourRules: String;
  pricePerDay: number;
  cancalletionFree: boolean;
  cancalletionFee: number;
  images: Image[] = new Array();


  constructor(id: number, name: String, address: Address, promoDescription: String, capacity: number, fishingEquipment: String, behaviourRules: String, pricePerDay: number, cancalletionFree: boolean, cancalletionFee: number) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.promoDescription = promoDescription;
    this.capacity = capacity;
    this.fishingEquipment = fishingEquipment;
    this.behaviourRules = behaviourRules;
    this.pricePerDay = pricePerDay;
    this.cancalletionFree = cancalletionFree;
    this.cancalletionFee = cancalletionFee;
  }
}
