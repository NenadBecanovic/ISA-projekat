import { Component, OnInit } from '@angular/core';
import {Boat} from "../model/boat";
import {Address} from "../model/address";
import {AdditionalService} from "../model/additional-service";
import {NavigationEquipment} from "../model/navigation-equipment";
import {BoatReservation} from "../model/boat-reservation";
import {BoatService} from "../service/boat.service";
import {AdditionalServicesService} from "../service/additional-services.service";
import {ImageService} from "../service/image.service";
import {Image} from "../model/image";
import {HouseReservationService} from "../service/house-reservation.service";
import {BoatReservationService} from "../service/boat-reservation.service";
import {HouseReservationSlide} from "../model/house-reservation-slide";
import {BoatReservationSlide} from "../model/boat-reservation-slide";
import {Router} from "@angular/router";


@Component({
  selector: 'app-boat-profile-for-boat-owner',
  templateUrl: './boat-profile-for-boat-owner.component.html',
  styleUrls: ['./boat-profile-for-boat-owner.component.css']
})

export class BoatProfileForBoatOwnerComponent implements OnInit {
  lat = 0;
  lng = 0;
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0,true, true, true, true);
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.address, this.navigationEquipment, this.additionalServices, 0);
  courses: BoatReservation[] = new Array<BoatReservation>();
  courses_slides: BoatReservationSlide[] = new Array<BoatReservationSlide>();
  isSlideLoaded: boolean = false;
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;
  freeCancelation: boolean = false;

  constructor(private _boatService: BoatService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService,
              private _boatReservationService: BoatReservationService, private _router: Router) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  addImageToBoat($event: Event) {

  }

  addActionDialog() {
    this._router.navigate(['/add-action-boat-profile', this.boat.id])
  }

  modifyProfile() {
    this._router.navigate(['/modify-boat-profile', this.boat.id])
  }

  deleteActionDialog(id: number) {
    this._boatReservationService.delete(id).subscribe(
      (boolean:boolean) =>{
        this.loadData()
      }
    )
  }

  loadData() { // ucitavanje iz baze
    this._boatService.getBoatById(2).subscribe(
      (boat: Boat) => {
        this.boat = boat
        this.address = this.boat.address;
        this.lat = this.address.latitude;
        this.lng = this.address.longitude;
        this.freeCancelation = this.boat.cancalletionFree;

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
