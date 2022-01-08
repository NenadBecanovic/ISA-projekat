import { Component, OnInit } from '@angular/core';
import {AdditionalService} from "../model/additional-service";
import {BoatReservation} from "../model/boat-reservation";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "ngx-alerts";
import {AdditionalServicesService} from "../service/additional-services.service";
import {BoatReservationService} from "../service/boat-reservation.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-add-action-boat-profile',
  templateUrl: './add-action-boat-profile.component.html',
  styleUrls: ['./add-action-boat-profile.component.css']
})
export class AddActionBoatProfileComponent implements OnInit {

  boatId: number = 0;
  additionalServices: AdditionalService[] = new Array();
  additionalServicesFinal: AdditionalService[] = new Array();
  boatReservation: BoatReservation = new BoatReservation(0, '', '', 0, this.additionalServices, 0, true);
  duration: number = 0;
  date: Date = new Date();
  endDate: Date = new Date();

  constructor(private _route: ActivatedRoute, private _boatReservationService: BoatReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              public datepipe: DatePipe) { }

  ngOnInit(): void {
    // @ts-ignore
    this.boatId = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  loadData(){
    this._additionalServicesService.getAllByBoatId(this.boatId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )
  }

  addAction() {
    this.boatReservation.boatId = this.boatId;
    this.boatReservation.action = true;
    this.boatReservation.available = true;
    this.boatReservation.availabilityPeriod = false;

    var startDate = Date.parse(this.boatReservation.startDate)
    this.date =  new Date(startDate)

    var actionStart  = Number(this.date)  // parsiranje datuma pocetka u milisekunde
    var actionEnd = actionStart + this.duration * 86400000

    this.boatReservation.startDate = actionStart.toString()
    this.boatReservation.endDate = actionEnd.toString()

    for (let a of this.additionalServices)
    {
      if (a.checked == true)
      {
        this.additionalServicesFinal.push(a)
      }
    }

    this.boatReservation.additionalServices = this.additionalServicesFinal

    this._boatReservationService.save(this.boatReservation).subscribe(
      (boatReservation: BoatReservation) => {
        this._router.navigate(['boat-profile-for-boat-owner/', this.boatId])
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      },
    )
  }

}
