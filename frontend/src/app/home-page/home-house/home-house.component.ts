import { Component, OnInit } from '@angular/core';
import {MatRadioChange} from "@angular/material/radio";

@Component({
  selector: 'app-home-house',
  templateUrl: './home-house.component.html',
  styleUrls: ['./home-house.component.css']
})
export class HomeHouseComponent implements OnInit {
  items = [1,2,3];

  constructor() { }

  ngOnInit(): void {
  }

  changeSort() {
    console.log("haloo")
  }

  radio1Changed() {

  }
}
