import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { AdventureUserInfo } from "./adventure-user-info";
import { FishingAdventure } from "./fishing-adventure";
import { MyUser } from "./my-user";

export class AdventureReservation {
    
    adventureId: number;
    id: number;
    startDate: String;
    endDate: String;
    maxGuests: number;
    price: number;
    isAvailable: boolean;
    additionalServices: Array<AdditionalService>;
    availabilityPeriod: boolean = false;
    isAction: boolean = false;
    guestId: number = 0;
    guest: AdventureUserInfo = new AdventureUserInfo(0, '', '');

    constructor(adventureId: number, id: number, startDate: String, endDate: String, maxGuests: number,price: number, 
        isAvailable: boolean, additionalServices: Array<AdditionalService>, avaibilityPeriod: boolean, isAction: boolean, guestId: number){
        this.adventureId = adventureId;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.price = price;
        this.isAvailable = isAvailable;
        this.additionalServices = additionalServices;
        this.availabilityPeriod = avaibilityPeriod;
        this.isAction = isAction;
        this.guestId = guestId;
    }
}
