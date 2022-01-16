import {Component, Inject, OnInit} from '@angular/core';
import {AdditionalService} from "../../../model/additional-service";
import {HouseReservation} from "../../../model/house-reservation";
import {MyUser} from "../../../model/my-user";
import {Address} from "../../../model/address";
import {ActivatedRoute, Router} from "@angular/router";
import {HouseReservationService} from "../../../service/house-reservation.service";
import {AlertService} from "ngx-alerts";
import {AdditionalServicesService} from "../../../service/additional-services.service";
import {MyUserService} from "../../../service/my-user.service";
import {DatePipe} from "@angular/common";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ReservationCheck} from "../../../model/reservation-check";
import {AuthentificationService} from "../../../auth/authentification/authentification.service";
import {HouseService} from "../../../service/house.service";
import {House} from "../../../model/house";
import {Room} from "../../../model/room";
import {ClientReservationService} from "../../../service/client-reservation-service";
import { CompanyService } from 'src/app/service/company.service';

@Component({
  selector: 'app-create-reservation-house',
  templateUrl: './create-reservation-house.component.html',
  styleUrls: ['./create-reservation-house.component.css']
})
export class CreateReservationHouseComponent implements OnInit {
  houseId: number = 0;
  additionalServices: AdditionalService[] = new Array();
  additionalServicesFinal: AdditionalService[] = new Array();
  houseReservation: HouseReservation = new HouseReservation(0, '', '', 0, this.additionalServices, 0, true);
  duration: number = 0;
  date: Date = new Date();
  dateString: string = ''
  endDate: Date = new Date();
  users: MyUser[] = new Array();
  finalUsers: MyUser[] = new Array();
  startDate: string = '';
  address: Address = new Address(0, '', '', '', 0, 0, 0);
  selectedUser: MyUser = new MyUser(0,'','','','','','', this.address,'','');
  reservationCheck: ReservationCheck =  new ReservationCheck()
  rooms: Room[] = new Array()
  house: House = new House(0,"", this.address,'','',0,false,0, this.rooms,
    this.additionalServices,0,0)
  companyPercentage: number = 0;


  constructor(@Inject(MAT_DIALOG_DATA) public dataDialog: any,private _route: ActivatedRoute, private _houseReservationService: HouseReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              private _myUserService: MyUserService, public datepipe: DatePipe,   public dialogRef: MatDialogRef<CreateReservationHouseComponent>,private _authentificationService: AuthentificationService,
              private _houseService: HouseService, private _clientResrvationService: ClientReservationService,  private _companyService: CompanyService
              ) { }

  ngOnInit(): void {
    // @ts-ignore
    this.houseId = this.dataDialog.houseId;
    this.reservationCheck.email = this.dataDialog.userEmail
    console.log(this.houseId)
    this.dateString = new Date().toISOString().slice(0, 16);
    this.startDate = new Date().toISOString().slice(0, 16);
    this.loadData();
    this.loadUser();
  }

  loadUser(){
    this._authentificationService.getUserByEmail().subscribe((user:MyUser)=>{
      this.selectedUser = user
    })
  }

  loadData(){
    this._additionalServicesService.getAllByHouseId(this.houseId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
        console.log(this.additionalServices)
      }
    )

    this._companyService.getCompanyPercentage().subscribe(
      (percentage: number) =>{
        this.companyPercentage = percentage;
      }
    )

    this._houseService.getHouseById(this.houseId).subscribe((house:House) =>{
      this.house = house
    })


  }

  addAction() {
    this.houseReservation.houseId = this.houseId;
    this.houseReservation.action = false;
    this.houseReservation.available = false;
    this.houseReservation.availabilityPeriod = false;
    this.houseReservation.available = false;
    this.houseReservation.guestId = this.selectedUser.id;

    var startDate = Date.parse(this.dateString)
    this.date =  new Date(startDate)

    var actionStart  = Number(this.date)  // parsiranje datuma pocetka u milisekunde
    var actionEnd = actionStart + this.duration * 86400000

    this.houseReservation.milisStartDate = actionStart
    this.houseReservation.milisEndDate= actionEnd

    var price = 0
    for (let a of this.additionalServices)
    {
      if (a.checked == true)
      {
        this.additionalServicesFinal.push(a)
        price = price + a.price
      }
    }

    this.houseReservation.additionalServices = this.additionalServicesFinal
    console.log(this.houseReservation)
    price = price + this.duration*this.house.pricePerDay
    var companyProfit = price * this.companyPercentage * 0.01;
    price = price - (companyProfit * this.selectedUser.userCategory.discountPercentage * 0.01);

    if (confirm("Da li ste sigurni da zelite da reservisete vikendicu. Cena je " + price.toString() + " dinara" )) {
     this._clientResrvationService.saveHouseReservation(this.houseReservation).subscribe((bool: boolean)=>{
       if(bool){
         this._alertService.success("Rezervacija je uspjesna, pogledajte mejl");
       }else{
         this._alertService.warning("Rezervacija je vec zauzeta");
       }
     }, (error => {
       this._alertService.danger("Doslo je do greske pokusajte opet");
     }))
    }


  }


}
