import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HouseService} from "../service/house.service";
import {HouseReservation} from "../model/house-reservation";
import {House} from "../model/house";
import {Address} from "../model/address";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {Alert, AlertService} from "ngx-alerts";
import {AlertServiceService} from "../service/alert-service.service";
import {RoomService} from "../service/room.service";
import {Room} from "../model/room";
import {AdditionalServicesService} from "../service/additional-services.service";
import {AdditionalService} from "../model/additional-service";

@Component({
  selector: 'app-modify-house-profile',
  templateUrl: './modify-house-profile.component.html',
  styleUrls: ['./modify-house-profile.component.css']
})
export class ModifyHouseProfileComponent implements OnInit {

  id: number = 0;
  address: Address = new Address(0, '', '','', 0, 0, 0);
  rooms: Room[] = new Array();
  additionalServices: AdditionalService[] = new Array();
  house: House = new House(0, '', this.address, '', '', 0, false, 0, this.rooms, this.additionalServices, 0);
  newAdditionalService: AdditionalService = new AdditionalService(0, '', 0, false);
  showNewService: boolean = false;
  showNewRoom: boolean = false;
  newRoom: Room = new Room(0, 0, this.house);

  constructor(private _route: ActivatedRoute, private _router: Router, private _houseService: HouseService, private _alertService: AlertService,
              private _roomService: RoomService, private _additionalServices: AdditionalServicesService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.id = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  private loadData() {
    this._houseService.getHouseById(this.id).subscribe(
      (house: House) => {
        this.house = house
        // console.log(house)

        this._roomService.getAllByHouseId(this.id).subscribe(
          (rooms: Room[]) => {
            this.rooms = rooms
            this.house.rooms = rooms
          }
        )

        this._additionalServices.getAllByHouseId(this.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.additionalServices = additionalServices
            this.house.services = additionalServices
          }
        )
      }
    )
    // console.log(this.house)
  }

  deleteAdditionalService(id: number) {
    this._additionalServices.delete(id).subscribe(   // OBAVEZNO SE MORA SUBSCRIBE-OVATI !!!
      (boolean:boolean) =>{
        this.loadData()
      }
    )
  }

  editProfile() {
    this._houseService.edit(this.house).subscribe(   // subscribe - da bismo dobili odgovor beka
      (house: House) => {
        this._router.navigate(['house-profile-for-house-owner'])
      },
      (error) => {
        // console.log(error)
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

  addAdditionalService() {
    this.newAdditionalService.houseId = this.id;
    this.newAdditionalService.checked = false;

    this._additionalServices.save(this.newAdditionalService).subscribe(   // subscribe - da bismo dobili odgovor beka
      (additionalService: AdditionalService) => {
        this.loadData();
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      },
    )
  }

  showAddingNewService() {
      if (this.showNewService == true)
      {
        this.showNewService = false;
      }
      else
      {
        this.showNewService = true;
      }
  }

  showAddingNewRoom() {
    if (this.showNewRoom == true)
    {
      this.showNewRoom = false;
    }
    else
    {
      this.showNewRoom = true;
    }
  }

  addNewRoom() {
    this.newRoom.houseId = this.id;

    this._roomService.save(this.newRoom).subscribe(
      (room: Room) => {
        this.loadData();
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      },
    )
  }

  deleteRoom(id: number) {
      this._roomService.delete(id).subscribe(
        (boolean:boolean) =>{
          this.loadData()
        }
      )
  }
}
