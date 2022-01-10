import {AdditionalService} from "./additional-service";
import {MyUser} from "./my-user";
import {Address} from "./address";

export class BoatReservation {

  public boatId: number = 0;
  public id: number;
  public startDate: string;
  public endDate: string;
  public maxGuests: number;
  public additionalServices: AdditionalService[];
  public price: number;
  public available: boolean;
  public availabilityPeriod: boolean = false;
  public action: boolean = false;
  public guestId: number = 0;
  public guest: MyUser = new MyUser(0, '', '', '', '', '','', new Address(0, '', '', '', 0, 0, 0), '', '');

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
