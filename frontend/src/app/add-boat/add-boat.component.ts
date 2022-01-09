import { Component, OnInit } from '@angular/core';
import {Boat} from "../model/boat";
import {Address} from "../model/address";
import {NavigationEquipment} from "../model/navigation-equipment";
import {AdditionalService} from "../model/additional-service";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {HouseService} from "../service/house.service";
import {AlertService} from "ngx-alerts";
import {Router} from "@angular/router";
import {BoatService} from "../service/boat.service";
import {House} from "../model/house";
import {MyUser} from "../model/my-user";
import {AuthentificationService} from "../authentification/authentification.service";

@Component({
  selector: 'app-add-boat',
  templateUrl: './add-boat.component.html',
  styleUrls: ['./add-boat.component.css']
})
export class AddBoatComponent implements OnInit {

  address: Address = new Address(0,"","","",0,0,0)
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0,false, false, false, false);
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.address, this.navigationEquipment, this.additionalServices, 0, 0);
  user: MyUser = new MyUser(0, '','','','','','', this.address, '','');

  constructor(private _boatService: BoatService, private _alertService: AlertService, private _router: Router, private _authentification: AuthentificationService) { }

  ngOnInit(): void {
    this.loadData();
  }

  private loadData() {
    this._authentification.getUserByEmail().subscribe(
      (user: MyUser) => {
        this.user = user;
      },
      (error) => {
      },
    )
  }

  createProfile() {
    this.boat.grade = 0;
    this.boat.ownerId = this.user.id;

    this._boatService.save(this.boat).subscribe(
      (boat: Boat) => {
        this._router.navigate(['home-page-boat-owner'])
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
}
