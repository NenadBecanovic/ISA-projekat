import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "ngx-alerts";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {MyUserService} from "../../service/my-user.service";
import {DatePipe} from "@angular/common";
import {AdditionalService} from "../../model/additional-service";
import {MyUser} from "../../model/my-user";
import {Address} from "../../model/address";
import {BoatReservation} from "../../model/boat-reservation";
import {BoatReservationService} from "../../service/boat-reservation.service";
import {AuthentificationService} from "../../auth/authentification/authentification.service";

@Component({
  selector: 'app-create-reservation-for-client-boat',
  templateUrl: './create-reservation-for-client-boat.component.html',
  styleUrls: ['./create-reservation-for-client-boat.component.css']
})
export class CreateReservationForClientBoatComponent implements OnInit {

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

  constructor(private _route: ActivatedRoute, private _boatReservationService: BoatReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              private _myUserService: MyUserService, public datepipe: DatePipe, private _authentification: AuthentificationService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.boatId = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  private loadData() {
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

    this._myUserService.getAllByBoatId(this.boatId).subscribe(
      (users: MyUser[]) => {
        this.users = users

        // proveriti da li za nekog klijenta postoji trenutna rezervacija koja traje
        for(let user of this.users)
        {
          this._boatReservationService.getBoatReservationsByGuestId(user.id).subscribe(
            (reservations: BoatReservation[]) => {
              // provera da li postoji termin trenutno, ako postoji dodati user-a u listu usera za koje vlasnik moze da zakaze termin
              for (let reservation of reservations)
              {
                this.datepipe.transform(this.date, 'dd/MM/yyyy HH:mm:ss');

                if (Number(reservation.startDate) < Number(Date.parse(this.date.toString()).toString()) &&  // ako rezervacija trenutno traje
                  Number(reservation.endDate) > Number(Date.parse(this.date.toString()).toString()) && !reservation.cancelled)
                {
                  if (this.userAlreadyInArray(user) == false) {
                    this.finalUsers.push(user)
                  }
                }
              }
            }
          )
        }
      }
    )
  }

  userAlreadyInArray(user: MyUser){
    for (let u of this.finalUsers)
    {
      if (u.id == user.id)
      {
        return true;
      }
    }
    return false;
  }

  addAction() {
    this.boatReservation.boatId = this.boatId;
    this.boatReservation.action = false;
    this.boatReservation.availabilityPeriod = false;
    this.boatReservation.available = false;
    this.boatReservation.availabilityPeriod = false;
    this.boatReservation.guestId = this.selectedUser.id;
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
    console.log(this.boatReservation)

    this._boatReservationService.save(this.boatReservation).subscribe(
      (boatReservation: BoatReservation) => {
        this._router.navigate(['boat-profile-for-boat-owner/', this.boatId])
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      }
    )
  }

  changeUser(id: number) {
    this.selectedUser.id = id;
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
