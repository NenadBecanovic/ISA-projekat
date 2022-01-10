import { Component, OnInit } from '@angular/core';
import {House} from "../../model/house";
import {HouseService} from "../../service/house.service";
import {Router} from "@angular/router";
import {Address} from "../../model/address";
import {MyUser} from "../../model/my-user";
import {AuthentificationService} from "../../auth/authentification/authentification.service";
import {ClientProfileComponent} from "../../clientHome/client-profile/client-profile.component";
import {MatDialog} from "@angular/material/dialog";
import {DeleteAccountComponent} from "../../clientHome/delete-account/delete-account.component";

@Component({
  selector: 'app-home-page-house-owner',
  templateUrl: './home-page-house-owner.component.html',
  styleUrls: ['./home-page-house-owner.component.css']
})
export class HomePageHouseOwnerComponent implements OnInit {

  houses: House[] = new Array();
  address: Address = new Address(0,"","","",0,0,31100)
  user: MyUser = new MyUser(0, '','','','','','',this.address, '','');
  housesSearch: House[] = new Array();
  houseNameSearch: string = "";
  houseAddressSearch: string = "";

  constructor(private _houseService: HouseService, private _router: Router, private _authentification: AuthentificationService, public dialog: MatDialog) { }

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

    this._houseService.getAll().subscribe(
      (houses: House[]) => {
            for(let h of houses)
            {
              if(h.ownerId == this.user.id)
              {
                this.houses.push(h);
              }
            }
        this.housesSearch = this.houses
      }
    )
  }

  addActionDialog() {
    this._router.navigate(['/add-house'])
  }

  deleteHouse(id: number) {
    this._houseService.delete(id).subscribe(
      (boolean:boolean) =>{
        this.loadData()
      }
    )
  }

  showHouse(id: number) {
    this._router.navigate(['/house-profile-for-house-owner', id])
  }

  openProfileDialog() {
    const dialogRef = this.dialog.open(ClientProfileComponent, {
      width: '600px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  deleteProfileDialog() {
    const dialogRef = this.dialog.open(DeleteAccountComponent,{
      width: '400px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  searchHouses() {
    this.houses = this.housesSearch.filter(s => s.name.toLowerCase().includes(this.houseNameSearch.toLowerCase()) &&
      (s.address.street + " " + s.address.city + " " + s.address.state).toLowerCase().includes(this.houseAddressSearch.toLowerCase()))
  }

  clearHouses() {
    this.houses = this.housesSearch
    this.houseNameSearch = "";
    this.houseAddressSearch = "";
  }
}
