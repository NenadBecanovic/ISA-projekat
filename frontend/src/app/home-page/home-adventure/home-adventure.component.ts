import { Component, OnInit } from '@angular/core';
import {Boat} from "../../model/boat";
import {AdventureService} from "../../service/adventure.service";
import {Adventure} from "../../model/adventure";

@Component({
  selector: 'app-home-adventure',
  templateUrl: './home-adventure.component.html',
  styleUrls: ['./home-adventure.component.css']
})
export class HomeAdventureComponent implements OnInit {

  constructor(private _adventureService: AdventureService) { }
  adventures: Adventure[] = new Array();

  ngOnInit(): void {
    this.loadData()
  }

  loadData() {
    this._adventureService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (adventures: Adventure[]) => {
        this.adventures = adventures;
      },
      (error) => {

      },
    )
  }
}
