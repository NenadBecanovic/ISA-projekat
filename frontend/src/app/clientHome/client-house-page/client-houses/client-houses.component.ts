import { Component, OnInit } from '@angular/core';
import {House} from "../../../model/house";
import {HouseService} from "../../../service/house.service";
import {Router} from "@angular/router";
import * as _ from "underscore";
import {AlertService} from "ngx-alerts";
import {ReservationCheck} from "../../../model/reservation-check";
import {CreateFeedbackComponent} from "../../dialog/create-feedback/create-feedback.component";
import {MatDialog} from "@angular/material/dialog";
import {
  CreateReservationHouseComponent
} from "../../dialog/create-reservation-house/create-reservation-house.component";
import {AuthentificationService} from "../../../auth/authentification/authentification.service";
import {MyUser} from "../../../model/my-user";

@Component({
  selector: 'app-client-houses',
  templateUrl: './client-houses.component.html',
  styleUrls: ['./client-houses.component.css']
})
export class ClientHousesComponent implements OnInit {
  houses: House[] = new Array();
  housesFilter: House[] = new Array();
  houseSearch: House[] = new Array();
  houseName: number = 0;
  houseGrade: number = 0;
  housePrice: number = 0
  houseFilterGrade: number = 0;
  houseNameSearch: string = "";
  houseAddressSearch: string = "";
  onGoingSearch: boolean = true;
  dateStartString: string = '';
  dateStart: Date = new Date();
  dayNumber: number = 0;
  maxGuest: string = '';
  request: ReservationCheck = new ReservationCheck();
  email: string = '';


  constructor(private _houseService: HouseService, private _router: Router, private _alertService: AlertService,
              public dialog: MatDialog, private _authentificationService: AuthentificationService) { }

  ngOnInit(): void {
    this.loadData()
    this.email = this._authentificationService.getUserEmail();
  }

  changeSortName() {
    this.houseGrade = 0;
    this.housePrice = 0;
    if(this.houseName === 1){
      this.houses = _.sortBy(this.houses, 'name',);
    }else if(this.houseName ===2){
      this.houses = _.sortBy(this.houses, 'name',).reverse();
    }
  }

  changeSortPrice() {
    this.houseGrade = 0;
    this.houseName = 0;
    if(this.housePrice === 1){
      console.log("uslo")
      this.houses = _.sortBy(this.houses, 'pricePerDay',);
    }else if(this.housePrice ===2 ){
      this.houses = _.sortBy(this.houses, 'pricePerDay',).reverse();
    }
  }

  changeSortGrade() {
    this.housePrice = 0;
    this.houseName = 0;
    if(this.houseGrade === 1){
      this.houses = _.sortBy(this.houses, 'avarageGrade',);
    }else if(this.houseGrade ===2){
      this.houses = _.sortBy(this.houses, 'avarageGrade',).reverse();
    }
  }

  radio1Changed() {
    var sortedArray = _(this.houses).chain().sortBy(function(house) {
      return house.name;
    }).sortBy(function(house) {
      return house.address;
    }).value();
  }

  private loadData() {
    this._houseService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (houses: House[]) => {
        this.houses = houses;
        this.housesFilter = houses
        this.houseSearch = houses
        console.log(this.houses)
      },
      (error) => {
      },
    )
  }

  goToHouse(id: number) {
    this._router.navigate(['client/house',id])
  }

  searchHouses(){
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
    this._houseService.getAvailableHouses(this.request).subscribe(   // subscribe - da bismo dobili odgovor beka
      (houses: House[]) => {
        this.houses = houses;
        this.housesFilter = houses
        this.houseSearch = houses
        console.log(this.houses)
      },
      (error) => {
      },
    )
  }

  filterGrade() {

    if(this.houseFilterGrade == 5){
      this.houses = this.housesFilter.filter(s => s.avarageGrade == 5 )
    }else if(this.houseFilterGrade == 4) {
      this.houses = this.housesFilter.filter(s => s.avarageGrade >= 4 && s.avarageGrade < 5)
    }else if(this.houseFilterGrade == 3) {
      this.houses = this.housesFilter.filter(s => s.avarageGrade >= 3 && s.avarageGrade < 4)
    }else if(this.houseFilterGrade == 2) {
      this.houses = this.housesFilter.filter(s => s.avarageGrade >= 2 && s.avarageGrade < 3)
    }else if(this.houseFilterGrade == 1) {
      this.houses = this.housesFilter.filter(s => s.avarageGrade >= 1 && s.avarageGrade < 2)
    }else if(this.houseFilterGrade == 0) {
      this.houses = this.housesFilter.filter(s => s.name.includes(''))
    }


  }

  restartAll() {
    this.housePrice = 0;
    this.houseName = 0;
    this.houseGrade = 0;
    this.houseFilterGrade = 0;
    this.dateStart = new Date();
    this.dayNumber= 0;
    this.maxGuest = '';
    this.loadData()

  }

  reservate(id: number) {
    const dialogRef = this.dialog.open(CreateReservationHouseComponent,{
      width: '600px',
      data: {
        houseId: id,
        userEmail: this.email
      },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }
}
