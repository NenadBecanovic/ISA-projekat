import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HouseService} from "../../service/house.service";
import {AlertService} from "ngx-alerts";
import {RoomService} from "../../service/room.service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {BoatService} from "../../service/boat.service";
import {Address} from "../../model/address";
import {Room} from "../../model/room";
import {AdditionalService} from "../../model/additional-service";
import {House} from "../../model/house";
import {Boat} from "../../model/boat";
import {NavigationEquipment} from "../../model/navigation-equipment";
import {MatCheckboxChange} from "@angular/material/checkbox";

@Component({
  selector: 'app-modify-boat-profile',
  templateUrl: './modify-boat-profile.component.html',
  styleUrls: ['./modify-boat-profile.component.css']
})
export class ModifyBoatProfileComponent implements OnInit {

  id: number = 0;
  address: Address = new Address(0, '', '','', 0, 0, 0);
  additionalServices: AdditionalService[] = new Array();
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0, false, false, false, false);
  boat: Boat = new Boat(0, '', '', '', 0, 0,'', 0,0,0,0,false,0, '', this.address, this.navigationEquipment, this.additionalServices, 0, 0);
  newAdditionalService: AdditionalService = new AdditionalService(0, '', 0, false);
  showNewService: boolean = false;

  constructor(private _route: ActivatedRoute, private _router: Router, private _boatService: BoatService, private _alertService: AlertService,
              private _additionalServices: AdditionalServicesService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.id = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  private loadData() {
    this._boatService.getBoatById(this.id).subscribe(
      (boat: Boat) => {
        this.boat = boat

        this._additionalServices.getAllByBoatId(this.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.boat.services = additionalServices
            this.additionalServices = additionalServices
          }
        )
      }
    )

  }

  deleteAdditionalService(id: number) {
    this._additionalServices.delete(id).subscribe(
      (boolean:boolean) =>{
        this.loadData()
      }
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

  addAdditionalService() {
    this.newAdditionalService.boatId = this.id;
    this.newAdditionalService.checked = false;

    this._additionalServices.save(this.newAdditionalService).subscribe(
      (additionalService: AdditionalService) => {
        this.loadData();
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      },
    )
  }

  checkboxChanged($event: MatCheckboxChange) {
    if (this.boat.cancalletionFree == true)
    {
      this.boat.cancalletionFee = 0
    }
  }

  editProfile() {
    console.log(this.boat.navigationEquipmentDTO)

    this._boatService.edit(this.boat).subscribe(
      (boat: Boat) => {
        this._router.navigate(['boat-profile-for-boat-owner', boat.id])
      },
      (error) => {
        // console.log(error)
        this._alertService.danger('Doslo je do greske');
      },
    )
  }
}
