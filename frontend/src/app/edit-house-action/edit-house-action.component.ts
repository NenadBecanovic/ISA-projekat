import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AdditionalService} from "../model/additional-service";
import {AdditionalServicesService} from "../service/additional-services.service";
import {HouseReservationService} from "../service/house-reservation.service";
import {HouseReservation} from "../model/house-reservation";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {checkbox} from "material-components-web/index";

@Component({
  selector: 'app-edit-house-action',
  templateUrl: './edit-house-action.component.html',
  styleUrls: ['./edit-house-action.component.css']
})
export class EditHouseActionComponent implements OnInit {

  houseReservationId: number = 0;
  houseId: number = 0;
  additionalServices: AdditionalService[] = new Array();
  allAdditionalServicesForHouse: AdditionalService[] = new Array();
  houseReservation: HouseReservation = new HouseReservation(0,'','', 0, this.additionalServices, 0, true);
  startDate: Date = new Date();
  endDate: Date = new Date();

  constructor(private _route: ActivatedRoute, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              private _houseReservationService: HouseReservationService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.houseReservationId = +this._route.snapshot.paramMap.get('id');
    // @ts-ignore
    this.houseId = +this._route.snapshot.paramMap.get('houseId');
    this.loadData();
  }

  loadData(){
    this._houseReservationService.getHouseReservationById(this.houseReservationId).subscribe(
      (houseReservation: HouseReservation) => {
        this.houseReservation = houseReservation
        // console.log(this.houseReservation)

        // datume iz straiga pretvoriti u date
        this.startDate = new Date(this.houseReservation.startDate)   // parsiranje datuma pocetka u milisekunde

        console.log(this.startDate)
        console.log(this.houseReservation.startDate)
        // this.date =  new Date(startDate)

      }
    )

    this._additionalServicesService.getAllByHouseReservationId(this.houseReservationId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )

    this._additionalServicesService.getAllByHouseId(this.houseId).subscribe(
      (allAdditionalServicesForHouse: AdditionalService[]) => {
        this.allAdditionalServicesForHouse = allAdditionalServicesForHouse


        for (let a of this.allAdditionalServicesForHouse)
        {
            for (let s of this.additionalServices)
            {
                if (a.id == s.id)
                {
                  a.checked = true
                }
            }
        }
        console.log(this.allAdditionalServicesForHouse)

      }
    )
   }

  editAction() {
    console.log(this.allAdditionalServicesForHouse)
  }

  checkboxChanged(event :MatCheckboxChange) {
    console.log(event.source.id)

  }
}
