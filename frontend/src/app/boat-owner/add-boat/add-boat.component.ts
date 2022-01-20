import { Component, OnInit } from '@angular/core';
import {Boat} from "../../model/boat";
import {Address} from "../../model/address";
import {NavigationEquipment} from "../../model/navigation-equipment";
import {AdditionalService} from "../../model/additional-service";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {HouseService} from "../../service/house.service";
import {AlertService} from "ngx-alerts";
import {Router} from "@angular/router";
import {BoatService} from "../../service/boat.service";
import {House} from "../../model/house";
import {MyUser} from "../../model/my-user";
import {AuthentificationService} from "../../auth/authentification/authentification.service";

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
    if (this.boat.name != '' && this.boat.promoDescription != '' && this.boat.address.street != '' && this.boat.address.city != '' &&
      this.boat.address.state != '' && this.boat.address.postalCode != 0 && this.boat.pricePerDay != 0 && this.boat.behaviourRules != '' &&
      this.boat.capacity != 0 && this.boat.type != '' && this.boat.length != 0 && this.boat.enginePower != 0 && this.boat.enginePower != 0 &&
      this.boat.maxSpeed != 0 && this.boat.fishingEquipment != '') {

      if (!this.boat.cancalletionFree && this.boat.cancalletionFee == 0)
      {
        this._alertService.warning('Unesite % nadoknade u slucaju otkazivanja');
      }
      else
      {
        if(this.boat.cancalletionFee > 100)
        {
          this._alertService.warning('Uslovi otkazivanja su u vrednostima 0-100');
        }
        else {
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
      }
    }
    else {
      this._alertService.warning('Niste popunili sva polja!');
    }
  }

  checkboxChanged($event: MatCheckboxChange) {
    if (this.boat.cancalletionFree == true)
    {
      this.boat.cancalletionFee = 0
    }
  }
}
