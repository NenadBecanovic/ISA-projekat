import { Component, OnInit } from '@angular/core';
import {AdditionalService} from "../../model/additional-service";
import {BoatReservation} from "../../model/boat-reservation";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "ngx-alerts";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {BoatReservationService} from "../../service/boat-reservation.service";
import {DatePipe} from "@angular/common";
import {MyUser} from "../../model/my-user";
import {AuthentificationService} from "../../auth/authentification/authentification.service";
import {Address} from "../../model/address";

@Component({
  selector: 'app-add-action-boat-profile',
  templateUrl: './add-action-boat-profile.component.html',
  styleUrls: ['./add-action-boat-profile.component.css']
})
export class AddActionBoatProfileComponent implements OnInit {

  boatId: number = 0;
  // captainOfBoatAdditionalService: AdditionalService = new AdditionalService(0, 'prisustvo vlasnika broda kao kapetana', 2000, false);
  additionalServices: AdditionalService[] = new Array();
  additionalServicesOriginal: AdditionalService[] = new Array();
  additionalServicesFinal: AdditionalService[] = new Array();
  additionalServicesAfterCheck: AdditionalService[] = new Array();
  boatReservation: BoatReservation = new BoatReservation(0, '', '', 0, this.additionalServices, 0, true);
  duration: number = 0;
  date: Date = new Date();
  endDate: Date = new Date();
  address: Address = new Address(0,"","","",0,0,0)
  user: MyUser = new MyUser(0, '','','','','','',this.address, '','');
  allDataSelected: boolean = false;
  // doneChecking: boolean = false;
  startDate: string = '';

  constructor(private _route: ActivatedRoute, private _boatReservationService: BoatReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              public datepipe: DatePipe, private _authentification: AuthentificationService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.boatId = +this._route.snapshot.paramMap.get('id');
    this.startDate = new Date().toISOString().slice(0, 16);
    this.loadData();
  }

  loadData(){
    this._additionalServicesService.getAllByBoatId(this.boatId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
        this.additionalServicesOriginal = additionalServices
      }
    )

    this._authentification.getUserByEmail().subscribe(
      (user: MyUser) => {
        this.user = user;
      },
      (error) => {
      },
    )
  }

  addAction() {
    this.boatReservation.boatId = this.boatId;
    this.boatReservation.action = true;
    this.boatReservation.available = true;
    this.boatReservation.availabilityPeriod = false;
    this.boatReservation.cancelled = false;

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

  dateChanged() {
    if (this.boatReservation.startDate != '' && this.duration != 0) {
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
            if (!b.cancelled) {
              if (actionStart >= Number(b.startDate) && actionEnd <= Number(b.endDate) ||
                actionStart <= Number(b.startDate) && actionEnd >= Number(b.startDate) ||
                actionStart >= Number(b.startDate) && actionStart <= Number(b.endDate)) {
                for (let a of b.additionalServices) {
                  // if u dodatnim uslugama postoji vlasnik kao kapetan izbaciti tu uslugu iz additionalServices +  return;
                  // doslo je do preklapanja, izbaciti dodatnu uslugu kapetana
                  if (a.name == "prisustvo kapetana") {
                    this.deleteCaptainAsAdditionalService();
                    return;
                  }
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
