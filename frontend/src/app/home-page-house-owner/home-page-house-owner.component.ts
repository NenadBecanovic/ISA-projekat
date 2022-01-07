import { Component, OnInit } from '@angular/core';
import {House} from "../model/house";
import {HouseService} from "../service/house.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-page-house-owner',
  templateUrl: './home-page-house-owner.component.html',
  styleUrls: ['./home-page-house-owner.component.css']
})
export class HomePageHouseOwnerComponent implements OnInit {

  houses: House[] = new Array();

  constructor(private _houseService: HouseService, private _router: Router) { }

  ngOnInit(): void {
    this.loadData();
  }

  private loadData() {
    this._houseService.getAll().subscribe(
      (houses: House[]) => {
        this.houses = houses
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
}
