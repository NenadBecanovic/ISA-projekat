import { Component, OnInit } from '@angular/core';
import {Address} from "../../model/address";
import {Boat} from "../../model/boat";
import {AdditionalService} from "../../model/additional-service";
import {BoatReservation} from "../../model/boat-reservation";
import {BoatReservationSlide} from "../../model/boat-reservation-slide";
import {Image} from "../../model/image";
import {BoatService} from "../../service/boat.service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {ImageService} from "../../service/image.service";
import {BoatReservationService} from "../../service/boat-reservation.service";
import {ActivatedRoute} from "@angular/router";
import {NavigationEquipment} from "../../model/navigation-equipment";

@Component({
  selector: 'app-boat',
  templateUrl: './boat.component.html',
  styleUrls: ['./boat.component.css']
})
export class BoatComponent implements OnInit {
  id = 0;
  lat = 0;
  lng = 0;
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  navigationEquipment: NavigationEquipment = new NavigationEquipment(0,true, true, true, true);
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.address, this.navigationEquipment, this.additionalServices, 0, 0);
  courses: BoatReservation[] = new Array<BoatReservation>();
  courses_slides: BoatReservationSlide[] = new Array<BoatReservationSlide>();
  isSlideLoaded: boolean = false;
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;
  freeCancelation: boolean = false;

  constructor(private _boatService: BoatService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService,
              private _boatReservationService: BoatReservationService, private _route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.id =  +this._route.snapshot.paramMap.get('id');
    this.loadData(this.id);
  }

  addImageToBoat($event: Event) {

  }

  showMap() {

  }


  loadData(id: number) { // ucitavanje iz baze
    this._boatService.getBoatById(id).subscribe(
      (boat: Boat) => {
        this.boat = boat
        console.log(boat)
        this.address = this.boat.address;
        this.lat = this.address.latitude;
        this.lng = this.address.longitude;
        this.freeCancelation = this.boat.cancalletionFree;
        // console.log(this.freeCancelation)

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
