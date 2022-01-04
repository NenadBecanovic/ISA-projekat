import { Component, OnInit } from '@angular/core';
import {House} from "../model/house";
import {Address} from "../model/address";
import {AdditionalService} from "../model/additional-service";
import {Room} from "../model/room";
import {Image} from "../model/image";
import {HouseService} from "../service/house.service";
import {AddressService} from "../service/address.service";
import {RoomService} from "../service/room.service";
import {AdditionalServicesService} from "../service/additional-services.service";
import {ImageService} from "../service/image.service";
import {HouseReservationService} from "../service/house-reservation.service";
import {HouseReservationSlide} from "../model/house-reservation-slide";
import {Router} from "@angular/router";

@Component({
  selector: 'app-house-profile-for-house-owner',
  templateUrl: './house-profile-for-house-owner.component.html',
  styleUrls: ['./house-profile-for-house-owner.component.css']
})
export class HouseProfileForHouseOwnerComponent implements OnInit {
  address: Address = new Address(0,"","","",0,0,31100)
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;
  rooms: Room[] = new Array<Room>();
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  house: House = new House(0,'', this.address, '', '', 0, false, 0, this.rooms, this.additionalServices);
  courses_slides: HouseReservationSlide[] = new Array<HouseReservationSlide>();
  isSlideLoaded: boolean = false;
  lat = 0;
  lng = 0;
  freeCancelation: boolean = false;
  duration: number = 0;

  constructor(private _houseService: HouseService, private _addressService: AddressService, private _roomService: RoomService,
              private _additionalServices: AdditionalServicesService, private _imageService: ImageService, private _houseReservationService: HouseReservationService,
              private _router: Router) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  // https://www.eduforbetterment.com/file-upload-using-material-components-in-angular/
  addImageToHouse(e: any) {
    const file: File = e.files[0];
    const reader = new FileReader();
  }

  addActionDialog() {
    this._router.navigate(['/add-action-house-profile', this.house.id])
  }

  modifyProfile() {
    this._router.navigate(['/modify-house-profile', this.house.id])
  }

  editActionDialog(id: number, houseId: number) {
    this._router.navigate(['/edit-house-action', this.house.id, this.house.id])
  }

  deleteActionDialog(id: number) {
    this._houseReservationService.delete(id).subscribe(   // OBAVEZNO SE MORA SUBSCRIBE-OVATI !!!
      (boolean:boolean) =>{
        this.loadData()
      }
    )
  }

  loadData() { // ucitavanje iz baze
    this._houseService.getHouseById(1).subscribe(
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

            // for (let c of this.courses_slides)
            // {
            //     for (let h of c.houseReservationDTOList)
            //     {
            //       console.log(h)
            //
            //       // for (let a of h.additionalServices)
            //       //   {
            //       //     console.log('ISPIS')
            //       //     // console.log(a.name, a.price)
            //       //   }
            //     }
            // }

            this.isSlideLoaded = true
            // console.log(courses_slides)
          }
        )
      }
    )

  }
}
