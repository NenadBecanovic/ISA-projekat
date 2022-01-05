import {Adventure} from "./adventure";

export class AdventureHomeSlide {

  adventures: Adventure[] = new Array();


  constructor(adventures: Adventure[]) {
    this.adventures = adventures;
  }
}
