import { Component, OnInit } from '@angular/core';
import {MatRadioChange} from "@angular/material/radio";
import {HouseService} from "../../service/house.service";
import {MyUser} from "../../model/my-user";
import {House} from "../../model/house";

@Component({
  selector: 'app-home-house',
  templateUrl: './home-house.component.html',
  styleUrls: ['./home-house.component.css']
})
export class HomeHouseComponent implements OnInit {
  items = [1,2,3];
  houses: House[] = new Array();

  constructor(private _houseService: HouseService) { }

  ngOnInit(): void {

    this.loadData()
  }

  changeSort() {
    console.log("haloo")
  }

  radio1Changed() {

  }

  private loadData() {
    this._houseService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (houses: House[]) => {
        this.houses = houses;
      },
      (error) => {

      },
    )
  }

  doStuff(id: number) {
    console.log(id)
  }
}
