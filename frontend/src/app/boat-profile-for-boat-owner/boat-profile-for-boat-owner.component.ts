import { Component, OnInit } from '@angular/core';
import {Boat} from "../model/boat";
import {Address} from "../model/address";
import {add} from "ngx-bootstrap/chronos";
import {AdditionalService} from "../model/additional-service";
import {NavigationEquipment} from "../model/navigation-equipment";
import {BoatReservation} from "../model/boat-reservation";
import {House} from "../model/house";
import {BoatService} from "../service/boat.service";
import {AdditionalServicesService} from "../service/additional-services.service";
import {ImageService} from "../service/image.service";
import {Image} from "../model/image";
import {HouseReservationService} from "../service/house-reservation.service";
import {BoatReservationService} from "../service/boat-reservation.service";
import {HouseReservationSlide} from "../model/house-reservation-slide";
import {BoatReservationSlide} from "../model/boat-reservation-slide";


@Component({
  selector: 'app-boat-profile-for-boat-owner',
  templateUrl: './boat-profile-for-boat-owner.component.html',
  styleUrls: ['./boat-profile-for-boat-owner.component.css']
})

export class BoatProfileForBoatOwnerComponent implements OnInit {
  lat = 0;
  lng = 0;
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.address);
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  // fishingEquipment: String = new String("10 stapova za pecanje, 100 udica");
  // navigationEquipment: NavigationEquipment = new NavigationEquipment(true, true, true, true);
  courses: BoatReservation[] = new Array<BoatReservation>();
  courses_slides: BoatReservationSlide[] = new Array<BoatReservationSlide>();
  isSlideLoaded: boolean = false;
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;

  constructor(private _boatService: BoatService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService,
              private _boatReservationService: BoatReservationService) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  addImageToBoat($event: Event) {

  }

  showMap() {

  }

  addActionDialog() {

  }

  modifyProfile() {

  }

  loadData() { // ucitavanje iz baze
    this._boatService.getBoatById(1).subscribe(
      (boat: Boat) => {
        this.boat = boat
        this.address = this.boat.address;
        this.lat = this.address.latitude;
        this.lng = this.address.longitude;

        this._additionalServices.getAllByBoatId(this.boat.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.additionalServices = additionalServices
          }
        )

        this._imageService.getAllByBoatId(this.boat.id).subscribe(
          (images: Image[]) => {
            this.images = images
            this.isLoaded = true;
          }
        )

        this._boatReservationService.getAllByBoatId(this.boat.id).subscribe(
          (courses_slides: BoatReservationSlide[]) => {
            this.courses_slides = courses_slides
            this.isSlideLoaded = true
          }
        )

      }
    )
  }


}
