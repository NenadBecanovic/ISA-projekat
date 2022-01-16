import {Component, Inject, OnInit} from '@angular/core';
import {FishingAdventure} from "../../../model/fishing-adventure";
import {AdditionalService} from "../../../model/additional-service";
import {AdventureReservation} from "../../../model/adventure-reservation";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AdventureReservationService} from "../../../service/adventure-reservation.service";
import {AdditionalServicesService} from "../../../service/additional-services.service";
import {AlertService} from "ngx-alerts";
import {ActivatedRoute, Router} from "@angular/router";
import {HouseReservationService} from "../../../service/house-reservation.service";
import {MyUserService} from "../../../service/my-user.service";
import {DatePipe} from "@angular/common";
import {AuthentificationService} from "../../../auth/authentification/authentification.service";
import {HouseService} from "../../../service/house.service";
import {ClientReservationService} from "../../../service/client-reservation-service";
import {MyUser} from "../../../model/my-user";
import {Address} from "../../../model/address";

@Component({
  selector: 'app-create-reservation-instructor',
  templateUrl: './create-reservation-instructor.component.html',
  styleUrls: ['./create-reservation-instructor.component.css']
})
export class CreateReservationInstructorComponent implements OnInit {
  adventureId: number =0;
  adventure!: FishingAdventure;
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  reservationAdditionalServices: AdditionalService[] = new Array();
  adventureReservation: AdventureReservation = new AdventureReservation(0, '', '', 0, this.additionalServices, 0, true);
  durationHours: number = 0;
  durationMinutes: number = 0;
  date: Date = new Date();
  instructorId!: number;
  isDisabled: boolean = false;
  dateString: string = ''
  startDate: string = ''
  address: Address = new Address(0, '', '', '', 0, 0, 0);
  selectedUser: MyUser = new MyUser(0,'','','','','','', this.address,'','');

  constructor(@Inject(MAT_DIALOG_DATA) public dataDialog: any,private _route: ActivatedRoute, private _houseReservationService: HouseReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              private _myUserService: MyUserService, public datepipe: DatePipe,   public dialogRef: MatDialogRef<CreateReservationInstructorComponent>,private _authentificationService: AuthentificationService,
              private _houseService: HouseService, private _clientResrvationService: ClientReservationService){

  }

  ngOnInit() {
    this.adventureId = this.dataDialog.houseId;
    this.instructorId = this.dataDialog.instrctorId;
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

  onNoClick(): void {
    this.dialogRef.close();
  }

  loadData() { // ucitavanje iz baze
    this._additionalServicesService.getAllByFishingAdventureId(this.adventure.id).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )
  }

  makeReservation() {
    this.adventureReservation.adventureId = this.adventure.id;
    this.adventureReservation.isAction = false;
    this.adventureReservation.isAvailable = false;
    this.adventureReservation.availabilityPeriod = false;

    var startDate = Date.parse(this.dateString)
    this.date =  new Date(startDate)
    var actionStart  = Number(this.date)
    this.date.setHours(this.date.getHours() + this.durationHours);
    this.date.setMinutes(this.date.getMinutes() + this.durationMinutes);
    var actionEnd = Number(this.date)
    this.adventureReservation.milisStartDate = actionStart
    this.adventureReservation.milisEndDate = actionEnd

    var price = this.adventure.pricePerHour * this.durationHours + (this.adventure.pricePerHour / 60) * this.durationMinutes;
    this.adventureReservation.price = price;
    for (let a of this.additionalServices)
    {
      if (a.checked == true)
      {
        this.reservationAdditionalServices.push(a)
      }
    }

    this.adventureReservation.additionalServices = this.reservationAdditionalServices
    this.adventureReservation.price = price

    if (confirm("Da li ste sigurni da zelite da reservisete avanturu. Cena je " + price.toString() + " dinara" )) {
      this._clientResrvationService.saveAdventureReservation(this.adventureReservation).subscribe((bool: boolean)=>{
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
