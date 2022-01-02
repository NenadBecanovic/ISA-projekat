import {Address} from "./address";

export class Boat {

  id: number;
  name: String;
  type: String;
  length: number;        // duzina broda
  engineNumber: number;  // broj motora
  enginePower: number;
  maxSpeed: number;
  // navigationEquipment: NavigationEquipment;
  address: Address;
  promoDescription: String;
  behaviourRules: String;
  capacity: number;
  pricePerDay: number;
  // services: Array<AdditionalService>;
  isCancalletionFree: boolean;
  cancalletionFee: number;
  fishingEquipment: String;
  // courses: Array<BoatReservation>;

  constructor(id: number, name: String, promoDescription: String, behaviourRules: String, pricePerDay: number, capacity: number,  type: String,
              length: number, engineNumber: number, enginePower: number, maxSpeed: number, isCancalletionFree: boolean, cancalletionFee: number, fishingEquipment: String, address: Address) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.length = length;
    this.engineNumber = engineNumber;
    this.enginePower = enginePower;
    this.maxSpeed = maxSpeed;
    this.capacity = capacity;
    this.address = address;
    this.promoDescription = promoDescription;
    this.behaviourRules = behaviourRules;
    this.pricePerDay = pricePerDay;
    // this.services = services;
    this.isCancalletionFree = isCancalletionFree;
    this.cancalletionFee = cancalletionFee;
    this.fishingEquipment = fishingEquipment;
    // this.navigationEquipment = navigationEquipment;
    // this.courses = courses;
  }
}
