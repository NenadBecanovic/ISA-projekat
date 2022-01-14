import { Component, OnInit } from '@angular/core';
import {House} from "../../model/house";
import {Boat} from "../../model/boat";
import {HouseService} from "../../service/house.service";
import {Router} from "@angular/router";
import {BoatService} from "../../service/boat.service";
import {MyUser} from "../../model/my-user";
import {AuthentificationService} from "../../auth/authentification/authentification.service";
import {Address} from "../../model/address";
import {ClientProfileComponent} from "../../clientHome/dialog/client-profile/client-profile.component";
import {DeleteAccountComponent} from "../../clientHome/dialog/delete-account/delete-account.component";
import {MatDialog} from "@angular/material/dialog";
import {Alert, AlertService} from "ngx-alerts";

@Component({
  selector: 'app-home-page-boat-owner',
  templateUrl: './home-page-boat-owner.component.html',
  styleUrls: ['./home-page-boat-owner.component.css']
})
export class HomePageBoatOwnerComponent implements OnInit {

  boats: Boat[] = new Array();
  address: Address = new Address(0,"","","",0,0,0)
  user: MyUser = new MyUser(0, '','','','','','',this.address, '','');
  boatSearch: Boat[] = new Array();
  boatNameSearch: string = "";
  boatAddressSearch: string = "";

  constructor(private _boatService: BoatService, private _router: Router, private _authentification: AuthentificationService,
              public dialog: MatDialog, private _alertService: AlertService) { }

  ngOnInit(): void {
    this.loadData();
  }

  showBoat(id: number) {
    this._router.navigate(['/boat-profile-for-boat-owner', id])
  }

  deleteBoat(id: number) {
    this._boatService.delete(id).subscribe(
      (boolean:boolean) =>{
        this.loadData()
      },
      (error) => {
        this._alertService.danger('Rezervisani brod se ne može obrisati');
      }
    )
  }

  private loadData() {
    this.boats = new Array();
    this._authentification.getUserByEmail().subscribe(
      (user: MyUser) => {
        this.user = user;

        this._boatService.findAll().subscribe(
          (boats: Boat[]) => {
            for(let h of boats)
            {
              if(h.ownerId == this.user.id)
              {
                if (this.boatAlreadyInArray(h) == false) {
                  this.boats.push(h);
                }
              }
            }
            this.boatSearch = this.boats
          }
        )
      },
      (error) => {
      },
    )
  }

  boatAlreadyInArray(boat: Boat){
    for (let u of this.boats)
    {
      if (u.id == boat.id)
      {
        return true;
      }
    }
    return false;
  }

  addActionDialog() {
    this._router.navigate(['/add-boat'])
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

  searchBoats() {
    this.boats = this.boatSearch.filter(s => s.name.toLowerCase().includes(this.boatNameSearch.toLowerCase()) &&
      (s.address.street + " " + s.address.city + " " + s.address.state).toLowerCase().includes(this.boatAddressSearch.toLowerCase()))
  }

  clearBoats() {
    this.boats = this.boatSearch
    this.boatNameSearch = "";
    this.boatAddressSearch = "";
  }
}
