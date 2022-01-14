import { Component, OnInit } from '@angular/core';
import {House} from "../../../model/house";
import {HouseService} from "../../../service/house.service";
import {Router} from "@angular/router";
import * as _ from "underscore";

@Component({
  selector: 'app-client-houses',
  templateUrl: './client-houses.component.html',
  styleUrls: ['./client-houses.component.css']
})
export class ClientHousesComponent implements OnInit {
  houses: House[] = new Array();
  housesFilter: House[] = new Array();
  houseSearch: House[] = new Array();
  houseName: number = 0;
  houseGrade: number = 0;
  housePrice: number = 0
  houseFilterGrade: number = 0;
  houseNameSearch: string = "";
  houseAddressSearch: string = "";
  onGoingSearch: boolean = true;

  constructor(private _houseService: HouseService, private _router: Router) { }

  ngOnInit(): void {
    this.loadData()
  }

  changeSortName() {
    if(this.houseName === 1){
      this.houses = _.sortBy(this.houses, 'name',);
    }else if(this.houseName ===2){
      this.houses = _.sortBy(this.houses, 'name',).reverse();
    }
  }

  changeSortPrice() {
    if(this.housePrice === 1){
      console.log("uslo")
      this.houses = _.sortBy(this.houses, 'pricePerDay',);
    }else if(this.housePrice ===2 ){
      this.houses = _.sortBy(this.houses, 'pricePerDay',).reverse();
    }
  }

  changeSortGrade() {
    if(this.houseGrade === 1){
      this.houses = _.sortBy(this.houses, 'grade',);
    }else if(this.houseGrade ===2){
      this.houses = _.sortBy(this.houses, 'grade',).reverse();
    }
  }

  radio1Changed() {
    var sortedArray = _(this.houses).chain().sortBy(function(house) {
      return house.name;
    }).sortBy(function(house) {
      return house.address;
    }).value();
  }

  private loadData() {
    this._houseService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (houses: House[]) => {
        this.houses = houses;
        this.housesFilter = houses
        this.houseSearch = houses
        console.log(this.houses)
      },
      (error) => {
      },
    )
  }

  goToHouse(id: number) {
    this._router.navigate(['client/house',id])
  }

  searchHouses(){
    this.houses = this.houseSearch.filter(s => s.name.toLowerCase().includes(this.houseNameSearch.toLowerCase()) &&
      (s.address.street+" "+ s.address.city + " " + s.address.state).toLowerCase().includes(this.houseAddressSearch.toLowerCase()))
    this.housesFilter = this.houses;
  }

  filterGrade() {

    if(this.houseFilterGrade == 5){
      this.houses = this.housesFilter.filter(s => s.grade == 5 )
    }else if(this.houseFilterGrade == 4) {
      this.houses = this.housesFilter.filter(s => s.grade >= 4 && s.grade < 5)
    }else if(this.houseFilterGrade == 3) {
      this.houses = this.housesFilter.filter(s => s.grade >= 3 && s.grade < 4)
    }else if(this.houseFilterGrade == 2) {
      this.houses = this.housesFilter.filter(s => s.grade >= 2 && s.grade < 3)
    }else if(this.houseFilterGrade == 1) {
      this.houses = this.housesFilter.filter(s => s.grade >= 1 && s.grade < 2)
    }else if(this.houseFilterGrade == 0) {
      this.loadData()
    }


  }

}
