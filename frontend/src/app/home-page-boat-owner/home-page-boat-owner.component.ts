import { Component, OnInit } from '@angular/core';
import {House} from "../model/house";
import {Boat} from "../model/boat";
import {HouseService} from "../service/house.service";
import {Router} from "@angular/router";
import {BoatService} from "../service/boat.service";

@Component({
  selector: 'app-home-page-boat-owner',
  templateUrl: './home-page-boat-owner.component.html',
  styleUrls: ['./home-page-boat-owner.component.css']
})
export class HomePageBoatOwnerComponent implements OnInit {

  boats: Boat[] = new Array();

  constructor(private _boatService: BoatService, private _router: Router) { }

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
    this._boatService.findAll().subscribe(
      (boats: Boat[]) => {
        this.boats = boats
      }
    )
  }

  addActionDialog() {
    this._router.navigate(['/add-boat'])
  }
}
