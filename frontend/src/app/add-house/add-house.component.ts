import { Component, OnInit } from '@angular/core';
import {AdditionalService} from "../model/additional-service";
import {Room} from "../model/room";
import {House} from "../model/house";
import {Address} from "../model/address";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {HouseService} from "../service/house.service";
import {AlertService} from "ngx-alerts";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-house',
  templateUrl: './add-house.component.html',
  styleUrls: ['./add-house.component.css']
})
export class AddHouseComponent implements OnInit {

  showNewService: boolean = false;
  additionalServices: AdditionalService[] = new Array();
  // showNewRoom: boolean = false;
  rooms: Room[] = new Array();
  address: Address = new Address(0, '', '', '', 0, 0, 0);
  house: House = new House(0, '', this.address, '', '',0,false, 0, this.rooms, this.additionalServices, 0);
  // newRoom: Room = new Room(0, 0, this.house);

  constructor(private _houseService: HouseService, private _alertService: AlertService, private _router: Router) { }

  ngOnInit(): void {
  }

  createProfile() {
    this.house.grade = 0;

    this._houseService.save(this.house).subscribe(   // subscribe - da bismo dobili odgovor beka
      (house: House) => {
        this._router.navigate(['home-page-house-owner'])
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      },
    )
  }

  checkboxChanged($event: MatCheckboxChange) {
    if (this.house.cancalletionFree == true)
    {
      this.house.cancalletionFee = 0
    }
  }

  // showAddingNewService() {
  //   if (this.showNewService == true)
  //   {
  //     this.showNewService = false;
  //   }
  //   else
  //   {
  //     this.showNewService = true;
  //   }
  // }
  // addAdditionalService() {
  //
  // }
  //
  // deleteAdditionalService(id: number) {
  //
  // }
  // addNewRoom() {
  //
  // }
  // deleteRoom(id: number) {
  //
  // }
  // showAddingNewRoom() {
  //   if (this.showNewRoom == true)
  //   {
  //     this.showNewRoom = false;
  //   }
  //   else
  //   {
  //     this.showNewRoom = true;
  //   }
  //}
}
