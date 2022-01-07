import { Component, OnInit } from '@angular/core';
import {BoatService} from "../../service/boat.service";
import {Router} from "@angular/router";
import {Boat} from "../../model/boat";
import * as _ from "underscore";

@Component({
  selector: 'app-client-boats',
  templateUrl: './client-boats.component.html',
  styleUrls: ['./client-boats.component.css']
})
export class ClientBoatsComponent implements OnInit {

  constructor(private _boatService: BoatService, private _router: Router) { }

  boats: Boat[] = new Array();
  boatsFilter: Boat[] = new Array();
  boatsSearch: Boat[] = new Array()
  boatName: number = 0;
  boatGrade: number = 0;
  boatPrice: number = 0
  boatFilterGrade: number = 0;
  boatNameSearch: string = "";
  boatAddressSearch: string = "";
  onGoingSearch: any;


  ngOnInit(): void {
    this.loadData()
  }

  loadData() {
    this._boatService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (boats: Boat[]) => {
        this.boats = boats;
        this.boatsFilter = boats
        this.boatsSearch = boats
      },
      (error) => {

      },
    )
  }

  goToBoat(id: number) {
    this._router.navigate(['client/boat',id])
  }

  searchBoats() {

    this.boats = this.boatsSearch.filter(s => s.name.toLowerCase().includes(this.boatNameSearch.toLowerCase()) &&
      (s.address.street+" "+ s.address.city + " " + s.address.state).toLowerCase().includes(this.boatAddressSearch.toLowerCase()))
    this.boatsFilter = this.boats;
  }

  filterGrade() {
    if(this.boatFilterGrade == 5){
      this.boats = this.boatsFilter.filter(s => s.grade == 5 )
    }else if(this.boatFilterGrade == 4) {
      this.boats = this.boatsFilter.filter(s => s.grade >= 4 && s.grade < 5)
    }else if(this.boatFilterGrade == 3) {
      this.boats = this.boatsFilter.filter(s => s.grade >= 3 && s.grade < 4)
    }else if(this.boatFilterGrade == 2) {
      this.boats = this.boatsFilter.filter(s => s.grade >= 2 && s.grade < 3)
    }else if(this.boatFilterGrade == 1) {
      this.boats = this.boatsFilter.filter(s => s.grade >= 1 && s.grade < 2)
    }else if(this.boatFilterGrade == 0) {
      this.loadData()
    }
  }

  changeSortPrice() {
    if(this.boatPrice === 1){
      this.boats = _.sortBy(this.boats, 'pricePerDay',);
    }else if(this.boatPrice ===2 ){
      this.boats = _.sortBy(this.boats, 'pricePerDay',).reverse();
    }
  }

  changeSortGrade() {
    if(this.boatGrade === 1){
      this.boats = _.sortBy(this.boats, 'grade',);
    }else if(this.boatGrade ===2){
      this.boats = _.sortBy(this.boats, 'grade',).reverse();
    }
  }

  changeSortName() {
    if(this.boatName === 1){
      this.boats = _.sortBy(this.boats, 'name',);
    }else if(this.boatName ===2){
      this.boats = _.sortBy(this.boats, 'name',).reverse();
    }
  }

}
