import { Component, OnInit } from '@angular/core';

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

}
