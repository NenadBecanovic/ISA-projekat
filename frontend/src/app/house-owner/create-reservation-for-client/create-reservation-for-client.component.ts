import { Component, OnInit } from '@angular/core';
import {AdditionalService} from "../../model/additional-service";
import {HouseReservation} from "../../model/house-reservation";
import {ActivatedRoute, Router} from "@angular/router";
import {HouseReservationService} from "../../service/house-reservation.service";
import {AlertService} from "ngx-alerts";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {MyUser} from "../../model/my-user";
import {MyUserService} from "../../service/my-user.service";
import {Address} from "../../model/address";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-create-reservation-for-client',
  templateUrl: './create-reservation-for-client.component.html',
  styleUrls: ['./create-reservation-for-client.component.css']
})
export class CreateReservationForClientComponent implements OnInit {

  houseId: number = 0;
  additionalServices: AdditionalService[] = new Array();
  additionalServicesFinal: AdditionalService[] = new Array();
  houseReservation: HouseReservation = new HouseReservation(0, '', '', 0, this.additionalServices, 0, true);
  duration: number = 0;
  date: Date = new Date();
  endDate: Date = new Date();
  users: MyUser[] = new Array();
  finalUsers: MyUser[] = new Array();
  address: Address = new Address(0, '', '', '', 0, 0, 0);
  selectedUser: MyUser = new MyUser(0,'','','','','','', this.address,'','');
  startDate: string = '';
  min: number = 0;

  constructor(private _route: ActivatedRoute, private _houseReservationService: HouseReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              private _myUserService: MyUserService, public datepipe: DatePipe) { }

  ngOnInit(): void {
    // @ts-ignore
    this.houseId = +this._route.snapshot.paramMap.get('id');
    this.startDate = new Date().toISOString().slice(0, 16);
    this.loadData();
  }

  loadData(){
    this._additionalServicesService.getAllByHouseId(this.houseId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )

    this._myUserService.getAllByHouseId(this.houseId).subscribe(
      (users: MyUser[]) => {
        this.users = users

        // proveriti da li za nekog klijenta postoji trenutna rezervacija koja traje
        for(let user of this.users)
        {
          this._houseReservationService.getHouseReservationsByGuestId(user.id).subscribe(
            (reservations: HouseReservation[]) => {
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
    if (this.duration > 0 && this.houseReservation.maxGuests > 0 && this.houseReservation.price > 0 && this.houseReservation.startDate != '' && this.selectedUser.id != 0) {
      this.houseReservation.houseId = this.houseId;
      this.houseReservation.action = false;
      this.houseReservation.availabilityPeriod = false;
      this.houseReservation.available = false;
      this.houseReservation.availabilityPeriod = false;
      this.houseReservation.guestId = this.selectedUser.id;
      this.houseReservation.cancelled = false;

      var startDate = Date.parse(this.houseReservation.startDate)
      this.date = new Date(startDate)

      var actionStart = Number(this.date)  // parsiranje datuma pocetka u milisekunde
      var actionEnd = actionStart + this.duration * 86400000

      this.houseReservation.startDate = actionStart.toString()
      this.houseReservation.endDate = actionEnd.toString()

      for (let a of this.additionalServices) {
        if (a.checked == true) {
          this.additionalServicesFinal.push(a)
        }
      }

      this.houseReservation.additionalServices = this.additionalServicesFinal

      this._houseReservationService.save(this.houseReservation).subscribe(
        (houseReservation: HouseReservation) => {
          this._router.navigate(['house-profile-for-house-owner/', this.houseId])
        },
        (error) => {
          this._alertService.danger('Doslo je do greske');
        }
      )
    } else {

      if(this.selectedUser.id == 0)
      {
        this._alertService.warning('Unesite klijenta');
      }
      if(this.houseReservation.startDate == '')
      {
        this._alertService.warning('Unesite datum i vreme');
      }
      else {
        this._alertService.warning('Neispravno uneti podaci');
      }
    }
  }

  changeUser(id: number) {
    this.selectedUser.id = id;
    console.log(this.selectedUser)
  }
}
