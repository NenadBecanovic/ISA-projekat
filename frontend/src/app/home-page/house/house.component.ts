import { Component, OnInit } from '@angular/core';
import {Address} from "../../model/address";
import {Image} from "../../model/image";
import {House} from "../../model/house";
import {Room} from "../../model/room";
import {AdditionalService} from "../../model/additional-service";
import {HouseReservationSlide} from "../../model/house-reservation-slide";
import {HouseService} from "../../service/house.service";
import {AddressService} from "../../service/address.service";
import {RoomService} from "../../service/room.service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {ImageService} from "../../service/image.service";
import {HouseReservationService} from "../../service/house-reservation.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-house',
  templateUrl: './house.component.html',
  styleUrls: ['./house.component.css']
})
export class HouseComponent implements OnInit {

  id: number = 0;
  address: Address = new Address(0,"","","",0,0,31100)
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;
  rooms: Room[] = new Array();
  additionalServices: AdditionalService[] = new Array();
  house: House = new House(0, '', this.address, '', '', 0, false, 0, this.rooms, this.additionalServices, 0);
  courses_slides: HouseReservationSlide[] = new Array<HouseReservationSlide>();
  isSlideLoaded: boolean = false;
  lat = 0;
  lng = 0;
  freeCancelation: boolean = false;
  duration: number = 0;

  constructor(private _houseService: HouseService, private _addressService: AddressService, private _roomService: RoomService,
              private _additionalServices: AdditionalServicesService, private _imageService: ImageService, private _houseReservationService: HouseReservationService,
              private _router: Router, private _route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.id =  +this._route.snapshot.paramMap.get('id');
    console.log(this.id)
    this.loadData(this.id);

  }


  addActionDialog() {
    this._router.navigate(['/add-action-house-profile', this.house.id])
  }


  deleteActionDialog() {

  }

  loadData(id: number) { // ucitavanje iz baze
    this._houseService.getHouseById(id).subscribe(
      (house:House) => {
        this.house = house
        this.address = this.house.address;
        this.lat = this.address.latitude;
        this.lng = this.address.longitude;
        this.freeCancelation = this.house.cancalletionFree;
        // console.log(this.freeCancelation)

        this._roomService.getAllByHouseId(this.house.id).subscribe(
          (rooms: Room[]) => {
            this.rooms = rooms
          }
        )

        this._additionalServices.getAllByHouseId(this.house.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.additionalServices = additionalServices
          }
        )

        this._imageService.getAllByHouseId(this.house.id).subscribe(
          (images: Image[]) => {
            this.images = images
            this.isLoaded = true;
          }
        )

        this._houseReservationService.getAllByHouseId(this.house.id).subscribe(
          (courses_slides: HouseReservationSlide[]) => {
            this.courses_slides = courses_slides
            this.isSlideLoaded = true
            console.log(courses_slides)
          }
        )
      }
    )

  }
}
