import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HouseReservation} from "../../model/house-reservation";
import {HouseReservationService} from "../../service/house-reservation.service";
import {AlertService} from "ngx-alerts";
import {AdditionalService} from "../../model/additional-service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {DatePipe} from "@angular/common";
import {HouseService} from "../../service/house.service";
import {House} from "../../model/house";
import {Room} from "../../model/room";
import {Address} from "../../model/address";
import {RoomService} from "../../service/room.service";

@Component({
  selector: 'app-add-action-house-profile',
  templateUrl: './add-action-house-profile.component.html',
  styleUrls: ['./add-action-house-profile.component.css']
})
export class AddActionHouseProfileComponent implements OnInit {

  houseId: number = 0;
  additionalServices: AdditionalService[] = new Array();
  additionalServicesFinal: AdditionalService[] = new Array();
  houseReservation: HouseReservation = new HouseReservation(0, '', '', 0, this.additionalServices, 0, true);
  rooms: Room[] = new Array();
  duration: number = 0;
  date: Date = new Date();
  endDate: Date = new Date();
  startDate: string = '';
  min: number = 0;
  capacity: number = 0;

  // _route: ActivatedRoute - da iz mog url-a preuzmem id
  constructor(private _route: ActivatedRoute, private _houseReservationService: HouseReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              public datepipe: DatePipe, private _roomService: RoomService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.houseId = +this._route.snapshot.paramMap.get('id');

    var date = new Date();
    var miliseconds = date.getTime();
    miliseconds = miliseconds + 3600000*2;
    date = new Date(miliseconds)

    this.startDate = date.toISOString().slice(0, 16);
    this.loadData();
  }

  loadData(){
    this._roomService.getAllByHouseId(this.houseId).subscribe(
      (rooms: Room[]) => {
        this.rooms = rooms
        console.log(rooms)

        for(let r of rooms){
          this.capacity = this.capacity + r.numberOfBeds;
        }
      }
    )

    this._additionalServicesService.getAllByHouseId(this.houseId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )
  }

  addAction() {
    if (this.duration > 0 && this.houseReservation.maxGuests > 0 && this.houseReservation.price > 0 && this.houseReservation.startDate != '') {

      if(this.houseReservation.maxGuests > this.capacity)
      {
        if (this.capacity == 0)
        {
          this._alertService.danger('Potrebno je dodati sobe u vikendicu');
        }
        else {
          this._alertService.danger('Maksimalni broj gostiju moze biti ' + this.capacity);
        }
      }
      else {
        this.houseReservation.houseId = this.houseId;
        this.houseReservation.action = true;
        this.houseReservation.available = true;
        this.houseReservation.availabilityPeriod = false;
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
        console.log(this.houseReservation)

        this._houseReservationService.save(this.houseReservation).subscribe(   // subscribe - da bismo dobili odgovor beka
          (houseReservation: HouseReservation) => {

            this._router.navigate(['house-profile-for-house-owner/', this.houseId])
          },
          (error) => {
            this._alertService.danger('Vec postoji termin u izabranom vremenskom periodu');
          }
        )
      }
    } else {

      if(this.houseReservation.startDate == '')
      {
        this._alertService.warning('Unesite datum i vreme');
      }else {
        this._alertService.warning('Neispravno uneti podaci');
      }
    }
  }

}
