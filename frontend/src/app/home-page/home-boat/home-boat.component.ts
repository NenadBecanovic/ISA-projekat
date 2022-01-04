import { Component, OnInit } from '@angular/core';
import {House} from "../../model/house";
import {BoatService} from "../../service/boat.service";
import {Boat} from "../../model/boat";

@Component({
  selector: 'app-home-boat',
  templateUrl: './home-boat.component.html',
  styleUrls: ['./home-boat.component.css']
})
export class HomeBoatComponent implements OnInit {

  constructor(private _boatService: BoatService) { }

  boats: Boat[] = new Array();

  ngOnInit(): void {
    this.loadData()
  }

  loadData() {
    this._boatService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (boats: Boat[]) => {
        this.boats = boats;
      },
      (error) => {

      },
    )
  }
}
