import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HouseReservation} from "../model/house-reservation";
import {HouseReservationService} from "../service/house-reservation.service";
import {AlertService} from "ngx-alerts";
import {AdditionalService} from "../model/additional-service";
import {AdditionalServicesService} from "../service/additional-services.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-add-action-house-profile',
  templateUrl: './add-action-house-profile.component.html',
  styleUrls: ['./add-action-house-profile.component.css']
})
export class AddActionHouseProfileComponent implements OnInit {

  houseId: number = 0;
  additionalServices: AdditionalService[] = new Array();
  additionalServicesFinal: AdditionalService[] = new Array();
  houseReservation: HouseReservation = new HouseReservation(0, '', '', 0, this.additionalServices, 0, true);
  duration: number = 0;
  date: Date = new Date();
  endDate: Date = new Date();

  // _route: ActivatedRoute - da iz mog url-a preuzmem id
  constructor(private _route: ActivatedRoute, private _houseReservationService: HouseReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              public datepipe: DatePipe) { }

  ngOnInit(): void {
    // @ts-ignore
    this.houseId = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  loadData(){
    this._additionalServicesService.getAllByHouseId(this.houseId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )
  }

  addAction() {
    this.houseReservation.houseId = this.houseId;
    this.houseReservation.action = true;

    // this.datepipe.transform(this.date, 'dd/MM/yyyy HH:mm:ss');
    //
    // var currentTimeInMilliseconds=Date.now(); // unix timestamp in milliseconds
    // console.log(currentTimeInMilliseconds)

    // this.houseReservation.startDate = this.date.now().toString()
    // this.endDate.setDate( this.date.getDate() + this.duration );

    // var endDate = Number(this.houseReservation.startDate) + (this.duration * 86400000);  // na Date se dodaje trajanje (koje je tipa number)
    // this.houseReservation.endDate = endDate.toString();

    var startDate = Date.parse(this.houseReservation.startDate)   // parsiranje datuma pocetka u milisekunde
    this.date =  new Date(startDate)
    this.endDate.setDate( this.date.getDate() + this.duration );  // na Date se dodaje trajanje (koje je tipa number)

    this.houseReservation.endDate = Date.parse(this.endDate.toString()).toString()
    this.houseReservation.startDate = Date.parse(this.date.toString()).toString()

    console.log(this.houseReservation.startDate)
    console.log(this.houseReservation.endDate)

    this.houseReservation.available = true;
    this.houseReservation.availabilityPeriod = false;

    // console.log(this.additionalServices)
    // this.houseReservation.additionalServices
    for (let a of this.additionalServices)
    {
        if (a.checked == true)
        {
          this.additionalServicesFinal.push(a)
        }
    }

    this.houseReservation.additionalServices = this.additionalServicesFinal
    console.log(this.houseReservation)

    this._houseReservationService.save(this.houseReservation).subscribe(   // subscribe - da bismo dobili odgovor beka
      (houseReservation: HouseReservation) => {

        this._router.navigate(['house-profile-for-house-owner/', this.houseId])
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      }
      // (HttpStatusCode.Conflict) = { this._alertService.danger('Vec postoji rezervacija u izabranom terminu')}
    )
  }

}
