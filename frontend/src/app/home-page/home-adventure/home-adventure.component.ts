import { Component, OnInit } from '@angular/core';
import {Boat} from "../../model/boat";
import {AdventureService} from "../../service/adventure.service";
import {Adventure} from "../../model/adventure";
import * as _ from "underscore";

@Component({
  selector: 'app-home-adventure',
  templateUrl: './home-adventure.component.html',
  styleUrls: ['./home-adventure.component.css']
})
export class HomeAdventureComponent implements OnInit {

  constructor(private _adventureService: AdventureService) { }
  adventures: Adventure[] = new Array();
  adventuresSearch: Adventure[] = new Array();
  adventureName: number = 0;
  adventurePrice: any;
  adventureNameSearch: string = "";
  adventureAddressSearch:string = "";

  ngOnInit(): void {
    this.loadData()
  }

  loadData() {
    this._adventureService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (adventures: Adventure[]) => {
        this.adventures = adventures;
        this.adventuresSearch = adventures;
      },
      (error) => {

      },
    )
  }

  changeSortName() {
    if(this.adventureName === 1){
      this.adventures = _.sortBy(this.adventures, 'name',);
    }else if(this.adventureName ===2){
      this.adventures = _.sortBy(this.adventures, 'name',).reverse();
    }
  }

  changeSortPrice() {
    if(this.adventurePrice === 1){
      console.log("uslo")
      this.adventures = _.sortBy(this.adventures, 'pricePerDay',);
    }else if(this.adventurePrice ===2 ){
      this.adventures = _.sortBy(this.adventures, 'pricePerDay',).reverse();
    }
  }

  searchAdventures() {
    this.adventures = this.adventuresSearch.filter(s => s.name.toLowerCase().includes(this.adventureNameSearch.toLowerCase()) &&
      (s.address.street+" "+ s.address.city + " " + s.address.state).toLowerCase().includes(this.adventureAddressSearch.toLowerCase()))
    // this.housesFilter = this.houses;
  }
}
