import {Address} from "./address";
import {NavigationEquipment} from "./navigation-equipment";
import {AdditionalService} from "./additional-service";

export class Boat {

  id: number;
  name: String;
  type: String;
  length: number;        // duzina broda
  engineNumber: number;  // broj motora
  enginePower: number;
  maxSpeed: number;
  navigationEquipmentDTO: NavigationEquipment;
  address: Address;
  promoDescription: String;
  behaviourRules: String;
  capacity: number;
  pricePerDay: number;
  services: AdditionalService[];
  cancalletionFree: boolean;
  cancalletionFee: number;
  fishingEquipment: String;
  // courses: Array<BoatReservation>;

  constructor(id: number, name: String, promoDescription: String, behaviourRules: String, pricePerDay: number, capacity: number,  type: String,
              length: number, engineNumber: number, enginePower: number, maxSpeed: number, cancalletionFree: boolean, cancalletionFee: number,
              fishingEquipment: String, address: Address, navigationEquipment: NavigationEquipment, services: AdditionalService[]) {
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
    this.cancalletionFree = cancalletionFree;
    this.cancalletionFee = cancalletionFee;
    this.fishingEquipment = fishingEquipment;
    this.navigationEquipmentDTO = navigationEquipment;
    this.services = services;
    // this.courses = courses;
  }
}
