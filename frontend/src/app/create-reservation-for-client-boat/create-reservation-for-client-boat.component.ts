import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HouseReservationService} from "../service/house-reservation.service";
import {AlertService} from "ngx-alerts";
import {AdditionalServicesService} from "../service/additional-services.service";
import {MyUserService} from "../service/my-user.service";
import {DatePipe} from "@angular/common";
import {AdditionalService} from "../model/additional-service";
import {HouseReservation} from "../model/house-reservation";
import {MyUser} from "../model/my-user";
import {Address} from "../model/address";
import {BoatReservation} from "../model/boat-reservation";
import {BoatReservationService} from "../service/boat-reservation.service";

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

  constructor(private _route: ActivatedRoute, private _boatReservationService: BoatReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              private _myUserService: MyUserService, public datepipe: DatePipe) { }

  ngOnInit(): void {
    // @ts-ignore
    this.boatId = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  private loadData() {
    this._additionalServicesService.getAllByBoatId(this.boatId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
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
                  Number(reservation.endDate) > Number(Date.parse(this.date.toString()).toString()) )
                {
                  this.finalUsers.push(user)
                }
              }
            }
          )
        }
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
}
