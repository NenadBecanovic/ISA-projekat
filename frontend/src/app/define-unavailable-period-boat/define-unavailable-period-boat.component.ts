import { Component, OnInit } from '@angular/core';
import {Address} from "../model/address";
import {AdditionalService} from "../model/additional-service";
import {BoatReservation} from "../model/boat-reservation";
import {Boat} from "../model/boat";
import {NavigationEquipment} from "../model/navigation-equipment";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "ngx-alerts";
import {BoatReservationService} from "../service/boat-reservation.service";
import {HouseReservation} from "../model/house-reservation";

@Component({
  selector: 'app-define-unavailable-period-boat',
  templateUrl: './define-unavailable-period-boat.component.html',
  styleUrls: ['./define-unavailable-period-boat.component.css']
})
export class DefineUnavailablePeriodBoatComponent implements OnInit {

  adress: Address = new Address(0, '', '', '', 0, 0, 0);
  additionalServices: AdditionalService[] = new Array();
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0, false, false, false, false);
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.adress, this.navigationEquipment, this.additionalServices, 0, 0);
  boatReservation: BoatReservation = new BoatReservation(0, '', '', 0, this.additionalServices, 0, true);
  date: Date = new Date();
  endDate: Date = new Date();

  constructor(private _route: ActivatedRoute, private _boatReservationService: BoatReservationService, private _router: Router, private _alertService: AlertService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.boat.id = +this._route.snapshot.paramMap.get('id');
  }

  addPeriod() {
    this.boatReservation.availabilityPeriod = true;
    this.boatReservation.available = false;
    this.boatReservation.boatId = this.boat.id;
    this.boatReservation.action = false;

    var startDate = Date.parse(this.boatReservation.startDate)   // parsiranje datuma pocetka u milisekunde
    this.date =  new Date(startDate)

    var endingDate = Date.parse(this.boatReservation.endDate)   // parsiranje datuma pocetka u milisekunde
    this.endDate =  new Date(endingDate)

    this.boatReservation.startDate = Date.parse(this.date.toString()).toString()
    this.boatReservation.endDate = Date.parse(this.endDate.toString()).toString()

    this._boatReservationService.save(this.boatReservation).subscribe(
      (boatReservation: BoatReservation) => {
        this._router.navigate(['boat-profile-for-boat-owner/', this.boat.id])
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      },
    )
  }
}
