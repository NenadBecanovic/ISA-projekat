import { Component, OnInit } from '@angular/core';
import {BoatService} from "../../../service/boat.service";
import {Router} from "@angular/router";
import {Boat} from "../../../model/boat";
import * as _ from "underscore";
import {ReservationCheck} from "../../../model/reservation-check";
import {House} from "../../../model/house";
import {AlertService} from "ngx-alerts";
import {
  CreateReservationHouseComponent
} from "../../dialog/create-reservation-house/create-reservation-house.component";
import {MatDialog} from "@angular/material/dialog";
import {CreateReservationBoatComponent} from "../../dialog/create-reservation-boat/create-reservation-boat.component";

@Component({
  selector: 'app-client-boats',
  templateUrl: './client-boats.component.html',
  styleUrls: ['./client-boats.component.css']
})
export class ClientBoatsComponent implements OnInit {

  constructor(private _boatService: BoatService, private _router: Router, private _alertService: AlertService,
              public dialog: MatDialog) { }

  boats: Boat[] = new Array();
  boatsFilter: Boat[] = new Array();
  boatsSearch: Boat[] = new Array()
  boatName: number = 0;
  boatGrade: number = 0;
  boatPrice: number = 0
  boatFilterGrade: number = 0;
  boatNameSearch: string = "";
  boatAddressSearch: string = "";
  onGoingSearch: any;
  dateStartString: string = '';
  dateStart: Date = new Date();
  dayNumber: number = 0;
  maxGuest: string = '';
  request: ReservationCheck = new ReservationCheck();


  ngOnInit(): void {
    this.loadData()
  }

  loadData() {
    this._boatService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (boats: Boat[]) => {
        this.boats = boats;
        this.boatsFilter = boats
        this.boatsSearch = boats
      },
      (error) => {

      },
    )
  }

  goToBoat(id: number) {
    this._router.navigate(['client/boat',id])
  }

  searchBoats() {
    if(this.dayNumber <1){
      this._alertService.warning("Unesite broj dana veci od 0")
      return;
    }else{
      if(this.maxGuest != ''){
        try {
          var maxGuestNumber = Number.parseInt(this.maxGuest)
          if(maxGuestNumber < 1){
            this._alertService.warning("Unesite broj gostiju veci od 0")
            return;
          }
        }catch (e){
          this._alertService.warning("Unesite broj gostiju veci od 0")
          return;
        }
      }
      maxGuestNumber = 0;
    }
    var startDate = Date.parse(this.dateStartString)
    this.dateStart =  new Date(startDate)

    var actionStart  = Number(this.dateStart)
    this.request.startMilis = actionStart
    this.request.endMilis = actionStart + this.dayNumber*24*60*60*1000
    this.request.maxGuest = maxGuestNumber;
    this._boatService.findAllAvailableBoats(this.request).subscribe(   // subscribe - da bismo dobili odgovor beka
      (boats: Boat[]) => {
        this.boats = boats;
        this.boatsFilter = boats
        this.boatsSearch = boats
      },
      (error) => {

      },
    )
  }

  filterGrade() {
    if(this.boatFilterGrade == 5){
      this.boats = this.boatsFilter.filter(s => s.grade == 5 )
    }else if(this.boatFilterGrade == 4) {
      this.boats = this.boatsFilter.filter(s => s.grade >= 4 && s.grade < 5)
    }else if(this.boatFilterGrade == 3) {
      this.boats = this.boatsFilter.filter(s => s.grade >= 3 && s.grade < 4)
    }else if(this.boatFilterGrade == 2) {
      this.boats = this.boatsFilter.filter(s => s.grade >= 2 && s.grade < 3)
    }else if(this.boatFilterGrade == 1) {
      this.boats = this.boatsFilter.filter(s => s.grade >= 1 && s.grade < 2)
    }else if(this.boatFilterGrade == 0) {
      this.boats = this.boatsFilter.filter(s => s.name.includes(''))
    }
  }

  changeSortPrice() {
    this.boatGrade = 0;
    this.boatName = 0;
    if(this.boatPrice === 1){
      this.boats = _.sortBy(this.boats, 'pricePerDay',);
    }else if(this.boatPrice ===2 ){
      this.boats = _.sortBy(this.boats, 'pricePerDay',).reverse();
    }
  }

  changeSortGrade() {
    this.boatPrice = 0;
    this.boatName = 0;
    if(this.boatGrade === 1){
      this.boats = _.sortBy(this.boats, 'grade',);
    }else if(this.boatGrade ===2){
      this.boats = _.sortBy(this.boats, 'grade',).reverse();
    }
  }

  changeSortName() {
    this.boatPrice = 0;
    this.boatGrade = 0;
    if(this.boatName === 1){
      this.boats = _.sortBy(this.boats, 'name',);
    }else if(this.boatName ===2){
      this.boats = _.sortBy(this.boats, 'name',).reverse();
    }
  }

  restartAll() {
    this.boatPrice = 0;
    this.boatGrade = 0;
    this.boatName = 0;
    this.boatFilterGrade = 0;
    this.dateStart = new Date();
    this.dayNumber= 0;
    this.maxGuest = '';
    this.loadData()
  }

  reservate(id: number) {
    const dialogRef = this.dialog.open(CreateReservationBoatComponent,{
      width: '600px',
      data: {
        boatId: id,
      },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }
}
