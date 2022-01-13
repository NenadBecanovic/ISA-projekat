export class Report {

  id: number;
  comment: String;
  missedReservation: boolean;
  penaltyProposal: boolean;
  houseReservationId: number;
  boatReservationId: number;
  adventureReservationId: number;
  isReviewed: boolean = false;

  constructor(id: number, comment: String, missedReservation: boolean, penaltyProposal: boolean, houseReservationId: number, boatReservationId: number, adventureReservationId: number) {
    this.id = id;
    this.comment = comment;
    this.missedReservation = missedReservation;
    this.penaltyProposal = penaltyProposal;
    this.houseReservationId = houseReservationId;
    this.boatReservationId = boatReservationId;
    this.adventureReservationId = adventureReservationId;
  }
}
