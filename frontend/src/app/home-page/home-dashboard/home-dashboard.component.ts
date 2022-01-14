import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {BoatHomeSlide} from "../../model/boat-home-slide";
import {BoatService} from "../../service/boat.service";
import {HouseService} from "../../service/house.service";
import {HouseHomeSlide} from "../../model/house-home-slide";
import {AdventureHomeSlide} from "../../model/adventure-home-slide";
import {AdventureService} from "../../service/adventure.service";

@Component({
  selector: 'app-home-dashboard',
  templateUrl: './home-dashboard.component.html',
  styleUrls: ['./home-dashboard.component.css']
})
export class HomeDashboardComponent implements OnInit {

  constructor(private _router: Router, private _boatService: BoatService, private  _houseService: HouseService,
              private _adventureService: AdventureService) {

  }

  boatSlides: BoatHomeSlide[] = new Array();
  houseSlides: HouseHomeSlide[] = new Array();
  adventureSlides: AdventureHomeSlide[] = new Array();
  isLoaded: boolean = false;

  ngOnInit(): void {
    this.loadData()
  }

  loadData() {
    this._boatService.findAllBoatsForHomePage().subscribe(   // subscribe - da bismo dobili odgovor beka
      (boatSlides: BoatHomeSlide[]) => {
        this.boatSlides = boatSlides;
        console.log(this.boatSlides)
        this._houseService.findAllHousesForHomePage().subscribe(   // subscribe - da bismo dobili odgovor beka
          (homeSlides: HouseHomeSlide[]) => {
            this.houseSlides = homeSlides;

            this._adventureService.findAllAdventuresForHomePage().subscribe(   // subscribe - da bismo dobili odgovor beka
              (adventureSlides: AdventureHomeSlide[]) => {
                this.adventureSlides = adventureSlides;
                this.isLoaded = true;

              },
              (error) => {
              },
            )
          },
          (error) => {
          },
        )
      },
      (error) => {

      },
    )
  }




  goToAllHouses() {
    this._router.navigate(['houses']);
  }


  goToAllBoats() {
    this._router.navigate(['boats']);
  }

  goToAllAdventures() {
    this._router.navigate(['instructors']);
  }
}
