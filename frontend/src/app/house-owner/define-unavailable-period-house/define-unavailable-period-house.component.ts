import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {House} from "../../model/house";
import {Address} from "../../model/address";
import {Room} from "../../model/room";
import {AdditionalService} from "../../model/additional-service";
import {HouseReservation} from "../../model/house-reservation";
import {HouseService} from "../../service/house.service";
import {AlertService} from "ngx-alerts";
import {HouseReservationService} from "../../service/house-reservation.service";

@Component({
  selector: 'app-define-unavailable-period-house',
  templateUrl: './define-unavailable-period-house.component.html',
  styleUrls: ['./define-unavailable-period-house.component.css']
})
export class DefineUnavailablePeriodHouseComponent implements OnInit {

  adress: Address = new Address(0,'', '', '', 0, 0, 0);
  rooms: Room[] = new Array();
  additionalServices: AdditionalService[] = new Array();
  house: House = new House(0, '', this.adress, '', '', 0, false, 0, this.rooms, this.additionalServices, 0, 0);
  houseReservation: HouseReservation = new HouseReservation(0, '', '', 0, this.additionalServices, 0, true);
  date: Date = new Date();
  endDate: Date = new Date();
  startDate: string = '';

  constructor(private _route: ActivatedRoute, private _houseReservationService: HouseReservationService, private _router: Router, private _alertService: AlertService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.house.id = +this._route.snapshot.paramMap.get('id');
    this.startDate = new Date().toISOString().slice(0, 16);
  }

  addPeriod() {
    if (this.houseReservation.startDate >= this.houseReservation.endDate)
    {
      this._alertService.warning('Datum i vreme pocetka moraju biti manji od datuma i vremena kraja');
    }
    else {
      this.houseReservation.availabilityPeriod = true;
      this.houseReservation.available = false;
      this.houseReservation.houseId = this.house.id;
      this.houseReservation.action = false;

      var startDate = Date.parse(this.houseReservation.startDate)   // parsiranje datuma pocetka u milisekunde
      this.date = new Date(startDate)

      var endingDate = Date.parse(this.houseReservation.endDate)   // parsiranje datuma pocetka u milisekunde
      this.endDate = new Date(endingDate)

      this.houseReservation.startDate = Date.parse(this.date.toString()).toString()
      this.houseReservation.endDate = Date.parse(this.endDate.toString()).toString()

      this._houseReservationService.save(this.houseReservation).subscribe(   // subscribe - da bismo dobili odgovor beka
        (houseReservation: HouseReservation) => {
          this._router.navigate(['house-profile-for-house-owner/', this.house.id])
        },
        (error) => {
          this._alertService.danger('Doslo je do greske');
        },
      )
    }
  }
}
