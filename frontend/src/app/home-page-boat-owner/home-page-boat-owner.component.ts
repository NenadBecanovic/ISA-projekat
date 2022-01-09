import { Component, OnInit } from '@angular/core';
import {House} from "../model/house";
import {Boat} from "../model/boat";
import {HouseService} from "../service/house.service";
import {Router} from "@angular/router";
import {BoatService} from "../service/boat.service";
import {MyUser} from "../model/my-user";
import {AuthentificationService} from "../authentification/authentification.service";
import {Address} from "../model/address";

@Component({
  selector: 'app-home-page-boat-owner',
  templateUrl: './home-page-boat-owner.component.html',
  styleUrls: ['./home-page-boat-owner.component.css']
})
export class HomePageBoatOwnerComponent implements OnInit {

  boats: Boat[] = new Array();
  address: Address = new Address(0,"","","",0,0,31100)
  user: MyUser = new MyUser(0, '','','','','','',this.address, '','');

  constructor(private _boatService: BoatService, private _router: Router, private _authentification: AuthentificationService) { }

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
      }
    )
  }

  private loadData() {
    this._authentification.getUserByEmail().subscribe(
      (user: MyUser) => {
        this.user = user;
      },
      (error) => {
      },
    )

    this._boatService.findAll().subscribe(
      (boats: Boat[]) => {
        this.boats = boats
        console.log(boats)

        for(let h of boats)
        {
          if(h.ownerId == this.user.id)
          {
            this.boats.push(h);
          }
        }
      }
    )
  }

  addActionDialog() {
    this._router.navigate(['/add-boat'])
  }
}
