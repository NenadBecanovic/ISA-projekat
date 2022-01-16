import { UserCategory } from "./user-category";

export class Company {

    userCategories: UserCategory[] = new Array<UserCategory>();
    percentagePerReservation: number = 0;
    pointsPerReservationClient: number = 0;
    pointsPerReservationOwner: number = 0;
}
