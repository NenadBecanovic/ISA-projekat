import { Component, OnInit } from '@angular/core';
import {Boat} from "../../model/boat";
import {Address} from "../../model/address";
import {AdditionalService} from "../../model/additional-service";
import {NavigationEquipment} from "../../model/navigation-equipment";
import {BoatReservation} from "../../model/boat-reservation";
import {BoatService} from "../../service/boat.service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {ImageService} from "../../service/image.service";
import {Image} from "../../model/image";
import {BoatReservationService} from "../../service/boat-reservation.service";
import {BoatReservationSlide} from "../../model/boat-reservation-slide";
import {ActivatedRoute, Router} from "@angular/router";
import {MyUser} from "../../model/my-user";
import {MyUserService} from "../../service/my-user.service";
import {DatePipe} from "@angular/common";


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
  boat: Boat = new Boat(0, '', '', '', 0, 0, '', 0, 0, 0, 0, false, 0, '', this.address, this.navigationEquipment, this.additionalServices, 0, 0);
  courses: BoatReservation[] = new Array<BoatReservation>();
  courses_slides: BoatReservationSlide[] = new Array<BoatReservationSlide>();
  isSlideLoaded: boolean = false;
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;
  freeCancelation: boolean = false;
  reservedCourses: BoatReservation[] = new Array();
  allCourses: BoatReservation[] = new Array();
  boatServicesLoaded: boolean = false;
  date: Date = new Date();
  userUsernameSearch: string = "";
  userFirstNameSearch: string = "";
  userLastNameSearch: string = "";
  userSearch: BoatReservation[] = new Array();

  constructor(private _boatService: BoatService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService,
              private _boatReservationService: BoatReservationService, private _router: Router, private _route: ActivatedRoute,
              private _myUserService: MyUserService, public datepipe: DatePipe) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.boat.id = +this._route.snapshot.paramMap.get('id');
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
    this._boatService.getBoatById(this.boat.id).subscribe(
      (boat: Boat) => {
        this.boat = boat
        this.address = this.boat.address;
        this.lat = this.address.latitude;
        this.lng = this.address.longitude;
        this.freeCancelation = this.boat.cancalletionFree;

        this._additionalServices.getAllByBoatId(this.boat.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.boatServicesLoaded = true;
            this.additionalServices = additionalServices
          }
        )

        this._imageService.getAllByBoatId(this.boat.id).subscribe(
          (images: Image[]) => {
            this.images = images
            this.isLoaded = true;
          }
        )

        this._boatReservationService.getAllActionsByBoatId(this.boat.id).subscribe(
          (courses_slides: BoatReservationSlide[]) => {
            this.courses_slides = courses_slides
            console.log(this.courses_slides)
            this.isSlideLoaded = true
          }
        )

        this._boatReservationService.getAllByBoatIdPlane(this.boat.id).subscribe(
          (allCourses: BoatReservation[]) => {
            this.allCourses = allCourses

            for (let course of allCourses)
            {
              // dobavljamo sve rezervacije (to su HouseReservations koje nisu available) - spisak/istorija rezervacija
              if (course.available == false && course.availabilityPeriod == false)
              {
                this._myUserService.findUserByBoatReservationId(course.id).subscribe(
                  (user: MyUser) => {
                    course.guest = user
                  }
                )
                this.reservedCourses.push(course);
              }
            }
            this.userSearch = this.reservedCourses
          }
        )

      }
    )
  }

  defineUnavailablePeriod() {
    this._router.navigate(['/define-unavailable-period-boat', this.boat.id])
  }

  showCalendar() {

  }

  createReservation() {
    this._router.navigate(['/create-reservation-for-client-boat', this.boat.id])
  }

  checkDate(endDate: string) {
    this.datepipe.transform(this.date, 'dd/MM/yyyy HH:mm:ss');

    if (Number(endDate) < Number(Date.parse(this.date.toString()).toString()))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  makeReport(id: number) {
    this._router.navigate(['/boat-report', id, this.boat.id])
  }

  seeGuestProfile(id: number) {
    this._router.navigate(['/guest-profile-boat', id])
  }

  clearUsers() {
    this.reservedCourses = this.userSearch
    this.userFirstNameSearch = "";
    this.userLastNameSearch = "";
    this.userUsernameSearch = "";
  }

  searchUsers() {
    this.reservedCourses = this.userSearch.filter(s =>
      (s.guest.firstName).toLowerCase().includes(this.userFirstNameSearch.toLowerCase()) &&
      (s.guest.lastName).toLowerCase().includes(this.userLastNameSearch.toLowerCase()) &&
      (s.guest.username).toLowerCase().includes(this.userUsernameSearch.toLowerCase()))
  }
}
