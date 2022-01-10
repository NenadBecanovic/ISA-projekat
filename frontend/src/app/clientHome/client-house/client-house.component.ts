import { Component, OnInit } from '@angular/core';
import {Address} from "../../model/address";
import {Image} from "../../model/image";
import {Room} from "../../model/room";
import {AdditionalService} from "../../model/additional-service";
import {House} from "../../model/house";
import {HouseReservationSlide} from "../../model/house-reservation-slide";
import {HouseService} from "../../service/house.service";
import {AddressService} from "../../service/address.service";
import {RoomService} from "../../service/room.service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {ImageService} from "../../service/image.service";
import {HouseReservationService} from "../../service/house-reservation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MyUserService} from "../../service/my-user.service";
import {MyUser} from "../../model/my-user";
import {Subscription} from "../../model/subscription";
import {AuthentificationService} from "../../auth/authentification/authentification.service";

@Component({
  selector: 'app-client-house',
  templateUrl: './client-house.component.html',
  styleUrls: ['./client-house.component.css']
})
export class ClientHouseComponent implements OnInit {

  address: Address = new Address(0,"","","",0,0,31100)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  currentUser: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  subscription: Subscription = new Subscription(0,this.user,this.user)
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;
  rooms: Room[] = new Array<Room>();
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  house: House = new House(0,'', this.address, '', '', 0, false, 0, this.rooms, this.additionalServices, 0, 0);
  courses_slides: HouseReservationSlide[] = new Array<HouseReservationSlide>();
  isSlideLoaded: boolean = false;
  lat = 0;
  lng = 0;
  freeCancelation: boolean = false;
  duration: number = 0;
  id: number = 0;
  isSubscribed: Boolean = false;


  constructor(private _houseService: HouseService, private _addressService: AddressService, private _roomService: RoomService,
              private _additionalServices: AdditionalServicesService, private _imageService: ImageService, private _houseReservationService: HouseReservationService,
              private _router: Router, private _route: ActivatedRoute, private _myUserService: MyUserService, private _authentification: AuthentificationService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.id =  +this._route.snapshot.paramMap.get('id');
    this.loadData(this.id);
  }


  loadData(id: number) { // ucitavanje iz baze
    this._houseService.getHouseById(id).subscribe(
      (house: House) => {
        this.house = house
        this.address = this.house.address;
        this.lat = this.address.latitude;
        this.lng = this.address.longitude;
        this.freeCancelation = this.house.cancalletionFree;

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
          }
        )

        this._myUserService.findUserByHouseid(this.house.id).subscribe(
          (myUser: MyUser) => {
            this.user = myUser
          }
        )

        this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
          (user: MyUser) => {
            this.currentUser = user;
          },
          (error) => {
          },
        )

        this._myUserService.checkIfUserIsSubscribes(this.currentUser.id, this.user.id).subscribe(
          (isSubscribed: Boolean) => {
            this.isSubscribed = isSubscribed
          }
        )
      }
    )

  }

  subscribe() {

    if(confirm("Da li sigurne zelite da se pretplatite")) {

      this.subscription.owner = this.user;
      this.subscription.subscribedUser = this.currentUser
      this._myUserService.saveSubscription(this.subscription).subscribe(
        (sub: Subscription) => {
          this.subscription = sub;
        }
      )
    }
  }
}
