import {Boat} from "./boat";

export class NavigationEquipment {

  gps: boolean;
  radar: boolean;
  VHFRadio: boolean;
  fishFinder: boolean;
  //boat: Boat;

  constructor(gps: boolean, radar: boolean, VHFRadio: boolean, fishFinder: boolean) {
    this.gps = gps;
    this.radar = radar;
    this.VHFRadio = VHFRadio;
    this.fishFinder = fishFinder;
  }
}
