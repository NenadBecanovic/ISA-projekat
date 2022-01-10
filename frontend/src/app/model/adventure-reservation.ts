import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { AdventureUserInfo } from "./adventure-user-info";
import { FishingAdventure } from "./fishing-adventure";
import { MyUser } from "./my-user";

export class AdventureReservation {
    
    adventureId: number=0;
    id: number;
    startDate: string;
    endDate: string;
    maxGuests: number;
    price: number;
    isAvailable: boolean;
    additionalServices: Array<AdditionalService>;
    availabilityPeriod: boolean = false;
    isAction: boolean = false;
    guestId: number = 0;
    guest: AdventureUserInfo = new AdventureUserInfo(0, '', '');

    constructor(id: number, startDate: string, endDate: string, maxGuests: number, additionalServices: AdditionalService[],
        price: number, isAvailable: boolean){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.price = price;
        this.isAvailable = isAvailable;
        this.additionalServices = additionalServices;
    }
}
