import { Component, OnInit } from '@angular/core';
import {Boat} from "../model/boat";
import {Address} from "../model/address";
import {add} from "ngx-bootstrap/chronos";
import {AdditionalService} from "../model/additional-service";
import {NavigationEquipment} from "../model/navigation-equipment";
import {BoatReservation} from "../model/boat-reservation";


@Component({
  selector: 'app-boat-profile-for-boat-owner',
  templateUrl: './boat-profile-for-boat-owner.component.html',
  styleUrls: ['./boat-profile-for-boat-owner.component.css']
})

export class BoatProfileForBoatOwnerComponent implements OnInit {
  // public map: any = { lat: 51.678418, lng: 7.809007 };
  lat = 51.678418;
  lng = 7.809007;

  slides = [{'image': 'assets/boat2.jpg'}, {'image': 'assets/boat-inside1.jpg'}, {'image': 'assets/boat-inside2.jpg'}]
  boat: Boat;
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  service1: AdditionalService= new AdditionalService("ROSTILJ", 2000);
  service2: AdditionalService= new AdditionalService("OPREMA ZA RONJENJE", 3000);
  additionalServices: Array<AdditionalService>;
  fishingEquipment: String = new String("10 stapova za pecanje, 100 udica");
  navigationEquipment: NavigationEquipment = new NavigationEquipment(true, true, true, true);
  courses: Array<BoatReservation> = new Array<BoatReservation>();
  boatReservation1: BoatReservation;
  boatReservation2: BoatReservation;
  boatReservation3: BoatReservation;
  boatReservation4: BoatReservation;

  constructor() {
    this.additionalServices = new Array<AdditionalService>();
    this.additionalServices.push(this.service1);
    this.additionalServices.push(this.service2);

    this.boat = new Boat("Kruzer na Dunavu", this.address, "Najbolji kruzer na Dunavu koji postoji u ponudi!",
      "Zabranjeno pusenje.", 8600, 10, "kruzer", 10, 3, 120, 80, false, 3000,
      this.additionalServices, this.fishingEquipment, this.navigationEquipment, this.courses);

    this.boatReservation1 = new BoatReservation(new Date("Dec 19 2021 07:44:57"), new Date("Dec 20 2021 07:44:57"), 5, "Bazen", 2000, true, this.boat)
    this.boatReservation2 = new BoatReservation(new Date("Dec 21 2021 07:44:57"), new Date("Dec 22 2021 07:44:57"), 3, "Rostilj", 1000, true, this.boat)
    this.boatReservation3 = new BoatReservation(new Date("Dec 23 2021 07:44:57"), new Date("Dec 25 2021 07:44:57"), 6, "Peraja za ronjenje", 1000, true, this.boat)
    this.boatReservation4 = new BoatReservation(new Date("Dec 28 2021 07:44:57"), new Date("Dec 30 2021 07:44:57"), 2, "Djakuzi", 1000, true, this.boat)

    this.courses.push(this.boatReservation1);
    this.courses.push(this.boatReservation2);
    this.courses.push(this.boatReservation3);

    this.boat.courses = this.courses;
  }

  ngOnInit(): void {
  }

  addImageToBoat($event: Event) {

  }

  showMap() {

  }

  addActionDialog() {

  }
}
