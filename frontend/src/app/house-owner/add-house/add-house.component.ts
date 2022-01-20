import { Component, OnInit } from '@angular/core';
import {AdditionalService} from "../../model/additional-service";
import {Room} from "../../model/room";
import {House} from "../../model/house";
import {Address} from "../../model/address";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {HouseService} from "../../service/house.service";
import {AlertService} from "ngx-alerts";
import {Router} from "@angular/router";
import {MyUser} from "../../model/my-user";
import {AuthentificationService} from "../../auth/authentification/authentification.service";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-add-house',
  templateUrl: './add-house.component.html',
  styleUrls: ['./add-house.component.css']
})
export class AddHouseComponent implements OnInit {

  additionalServices: AdditionalService[] = new Array();
  rooms: Room[] = new Array();
  address: Address = new Address(0, '', '', '', 0, 0, 0);
  house: House = new House(0, '', this.address, '', '',0,false, 0, this.rooms, this.additionalServices, 0, 0);
  user: MyUser = new MyUser(0, '','','','','','', this.address, '','');

  constructor(private _houseService: HouseService, private _alertService: AlertService, private _router: Router,
              private _authentification: AuthentificationService, public dialog: MatDialog,
              ) { }

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
    if (this.house.name != '' && this.house.promoDescription != '' && this.house.address.street != '' && this.house.address.city != '' &&
      this.house.address.state != '' && this.house.address.postalCode != 0 && this.house.pricePerDay != 0 && this.house.behaviourRules != '') {

      if (!this.house.cancalletionFree && this.house.cancalletionFee == 0) {
        this._alertService.warning('Unesite % nadoknade u slucaju otkazivanja');
      }
      else
      {
        if (this.house.cancalletionFee > 100) {
          this._alertService.warning('Uslovi otkazivanja su u vrednostima 0-100');
        } else {
          this.house.grade = 0;
          this.house.ownerId = this.user.id;

          this._houseService.save(this.house).subscribe(   // subscribe - da bismo dobili odgovor beka
            (house: House) => {
              this._router.navigate(['home-page-house-owner'])
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
    if (this.house.cancalletionFree == true)
    {
      this.house.cancalletionFee = 0
    }
  }
}
