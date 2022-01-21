import {Component, Inject, OnInit} from '@angular/core';
import {AdditionalService} from "../../../model/additional-service";
import {BoatReservation} from "../../../model/boat-reservation";
import {MyUser} from "../../../model/my-user";
import {Address} from "../../../model/address";
import {ActivatedRoute, Router} from "@angular/router";
import {BoatReservationService} from "../../../service/boat-reservation.service";
import {AlertService} from "ngx-alerts";
import {AdditionalServicesService} from "../../../service/additional-services.service";
import {MyUserService} from "../../../service/my-user.service";
import {DatePipe} from "@angular/common";
import {AuthentificationService} from "../../../auth/authentification/authentification.service";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {BoatService} from "../../../service/boat.service";
import {ClientReservationService} from "../../../service/client-reservation-service";
import {NavigationEquipment} from "../../../model/navigation-equipment";
import {Boat} from "../../../model/boat";
import { CompanyService } from 'src/app/service/company.service';

@Component({
  selector: 'app-create-reservation-boat',
  templateUrl: './create-reservation-boat.component.html',
  styleUrls: ['./create-reservation-boat.component.css']
})
export class CreateReservationBoatComponent implements OnInit {
  boatId: number = 0;
  additionalServices: AdditionalService[] = new Array();
  additionalServicesFinal: AdditionalService[] = new Array();
  boatReservation: BoatReservation = new BoatReservation(0, '', '', 0, this.additionalServices, 0, true);
  duration: number = 0;
  date: Date = new Date();
  endDate: Date = new Date();
  users: MyUser[] = new Array();
  finalUsers: MyUser[] = new Array();
  address: Address = new Address(0, '', '', '', 0, 0, 0);
  selectedUser: MyUser = new MyUser(0,'','','','','','', this.address,'','');
  address1: Address = new Address(0,"","","",0,0,0)
  user: MyUser = new MyUser(0, '','','','','','',this.address1, '','');
  allDataSelected: boolean = false;
  additionalServicesOriginal: AdditionalService[] = new Array();
  additionalServicesAfterCheck: AdditionalService[] = new Array();
  dateString: string = '';
  startDate: string = '';
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0,true, true, true, true);
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.address, this.navigationEquipment, this.additionalServices, 0, 0);
  companyPercentage: number = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public dataDialog: any,private _route: ActivatedRoute, private _boatReservationService: BoatReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              private _myUserService: MyUserService, public datepipe: DatePipe, private _authentification: AuthentificationService, private _boatService: BoatService,
              private _clientReservationService: ClientReservationService, private _companyService: CompanyService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.boatId = this.dataDialog.boatId;
    this.dateString = new Date().toISOString().slice(0, 16);
    this.startDate = new Date().toISOString().slice(0, 16);
    this.loadData();
    this.loadUser();
  }

  loadUser(){
    this._authentification.getUserByEmail().subscribe((user:MyUser)=>{
      this.selectedUser = user
    })
  }

  private loadData() {
    this._additionalServicesService.getAllByBoatId(this.boatId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
        this.additionalServicesOriginal = additionalServices
      }
    )

    this._boatService.getBoatById(this.boatId).subscribe((boat:Boat)=>{
      this.boat = boat
    },(error => {}))

    this._companyService.getCompanyPercentage().subscribe(
      (percentage: number) =>{
        this.companyPercentage = percentage;
      }
    )
  }


  addAction() {
    this.boatReservation.boatId = this.boatId;
    this.boatReservation.action = false;
    this.boatReservation.availabilityPeriod = false;
    this.boatReservation.available = false;
    this.boatReservation.availabilityPeriod = false;
    this.boatReservation.guestId = this.selectedUser.id;

    var startDate = Date.parse(this.dateString)
    this.date =  new Date(startDate)

    var actionStart  = Number(this.date)  // parsiranje datuma pocetka u milisekunde
    var actionEnd = actionStart + this.duration * 86400000

    this.boatReservation.milisStartDate = actionStart
    this.boatReservation.milisEndDate = actionEnd

    var price = 0
    for (let a of this.additionalServices)
    {
      if (a.checked == true)
      {
        this.additionalServicesFinal.push(a)
        price = price + a.price
      }
    }

    this.boatReservation.additionalServices = this.additionalServicesFinal

    price = price = this.duration*this.boat.pricePerDay
    var companyProfit = price * this.companyPercentage * 0.01;
    price = price - (companyProfit * this.selectedUser.userCategory.discountPercentage * 0.01);

    if (confirm("Da li ste sigurni da zelite da reservisete vikendicu. Cena je " + price.toString() + " dinara" )) {
      this._clientReservationService.saveBoatReservation(this.boatReservation).subscribe((bool: boolean)=>{
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



  dateChanged() {
    if (this.dateString != '' && this.duration != 0) {
      this.allDataSelected = true;
      this.ckeckOwnerAsCaptainAdditionalService();
    } else {
      this.allDataSelected = false;
    }
  }

  ckeckOwnerAsCaptainAdditionalService() {
    // u dodatnim uslugama svih rezervacija svih vlasnikovih kuca, proveriti da li se i on nalazi u tom terminu

    this.additionalServices = new Array();
    this.additionalServicesAfterCheck = new Array();
    this.additionalServices = this.additionalServicesOriginal

    var startDate = Date.parse(this.boatReservation.startDate)
    this.date =  new Date(startDate)
    var actionStart  = Number(this.date)
    var actionEnd = actionStart + this.duration * 86400000

    this._boatReservationService.getBoatReservationByBoatOwnerId(this.user.id).subscribe(
      (boatReservations: BoatReservation[]) => {
        for(let b of boatReservations)
        {
          // ako postoje termini u periodu kada zakazujemo novi termin, potrebno je proveriti zauzetost vlasnika
          if (actionStart >= Number(b.startDate) && actionEnd <= Number(b.endDate) ||
            actionStart <= Number(b.startDate) && actionEnd >= Number(b.startDate) ||
            actionStart >= Number(b.startDate) && actionStart <= Number(b.endDate))
          {
            for(let a of b.additionalServices)
            {
              // if u dodatnim uslugama postoji vlasnik kao kapetan izbaciti tu uslugu iz additionalServices +  return;
              // doslo je do preklapanja, izbaciti dodatnu uslugu kapetana
              if (a.name == "prisustvo kapetana"){
                this.deleteCaptainAsAdditionalService();
                return;
              }
            }
          }
        }
      }
    )
  }

  deleteCaptainAsAdditionalService() {
    for(let service of this.additionalServices)
    {
      if(service.name != "prisustvo kapetana")
      {
        this.additionalServicesAfterCheck.push(service)
      }
    }
    this.additionalServices = this.additionalServicesAfterCheck
  }
}
