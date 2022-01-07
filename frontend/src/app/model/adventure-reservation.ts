import { AdditionalService } from "./additional-service";
import { FishingAdventure } from "./fishing-adventure";

export class AdventureReservation {
    id: number;
    startDate: Date;
    endDate: Date;
    maxGuests: number;
    additionalServices: Array<AdditionalService>;
    price: number;
    isAvailable: boolean;
    fishingAdventure: FishingAdventure;

    constructor(id: number, startDate: Date, endDate:Date, maxGuests: number, additionalServices: Array<AdditionalService>, price: number, isAvailable: boolean, fishingAdventure: FishingAdventure){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.additionalServices = additionalServices;
        this.price = price;
        this.isAvailable = isAvailable;
        this.fishingAdventure = fishingAdventure;
    }
}
