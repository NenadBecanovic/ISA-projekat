import { AdditionalService } from "./additional-service";
import { Address } from "./address";
import { UserInfo } from "./user-info";
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
    hasReport: boolean = false;
    guestId: number = 0;
    guest: UserInfo = new UserInfo(0, '', '', '', '');
    cancelled: boolean = false;
    canBeCancelled: boolean = false;
    onGoing: boolean = false;
    milisStartDate:number = 0;
    milisEndDate:number = 0;
    hasFeedbackOwner:  boolean = false;
    hasAppealOwner: boolean = false;
    totalPrice: number = 0;
    entityName: string = '';
    houserOwnerName: string = '';
    ownerId: number = 0;
    isOwnerReservation = false;


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
