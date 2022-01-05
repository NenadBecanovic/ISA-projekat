import {AdditionalService} from "./additional-service";

export class HouseReservation {

  public houseId: number = 0;
  public id: number;
  public startDate: string;
  public endDate: string;
  public maxGuests: number;
  public additionalServices: AdditionalService[];
  public price: number;
  public available: boolean;

  constructor(id: number, startDate: string, endDate: string, maxGuests: number, additionalServices: AdditionalService[],
              price: number, isAvailable: boolean) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.maxGuests = maxGuests;
    this.additionalServices = additionalServices;
    this.price = price;
    this.available = isAvailable;
  }

}
