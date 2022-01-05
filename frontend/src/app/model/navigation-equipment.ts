import {Boat} from "./boat";

export class NavigationEquipment {

  id: number;
  gps: boolean;
  radar: boolean;
  VHFRadio: boolean;
  fishFinder: boolean;
  //boat: Boat;

  constructor(id:number, gps: boolean, radar: boolean, VHFRadio: boolean, fishFinder: boolean) {
    this.id = id;
    this.gps = gps;
    this.radar = radar;
    this.VHFRadio = VHFRadio;
    this.fishFinder = fishFinder;
  }
}
