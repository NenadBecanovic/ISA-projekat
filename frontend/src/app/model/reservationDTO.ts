import { AdditionalService } from "./additional-service";

export class ReservationDTO {

    start: Date;
    end: Date;
    numberOfPeople: number;
    additionalServices: Array<AdditionalService>;
    fishingAdventure: number;

    constructor(start: Date,end: Date, numberOfPeople: number,additionalServices: Array<AdditionalService>, fishingAdventure: number){
        this.start = start;
        this.end = end;
        this.numberOfPeople = numberOfPeople;
        this.additionalServices = additionalServices;
        this.fishingAdventure = fishingAdventure;
    }
}
