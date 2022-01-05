import { Component, OnInit } from '@angular/core';
import {House} from "../model/house";
import {HouseService} from "../service/house.service";

@Component({
  selector: 'app-home-page-house-owner',
  templateUrl: './home-page-house-owner.component.html',
  styleUrls: ['./home-page-house-owner.component.css']
})
export class HomePageHouseOwnerComponent implements OnInit {

  houses: House[] = new Array();

  constructor(private _houseService: HouseService) { }

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

  }

  deleteHouse(id: number) {
    this._houseService.delete(id).subscribe(
      (boolean:boolean) =>{
        this.loadData()
      }
    )
  }
}
