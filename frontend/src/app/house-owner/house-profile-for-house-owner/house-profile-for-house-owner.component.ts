import { Component, OnInit } from '@angular/core';
import {House} from "../../model/house";
import {Address} from "../../model/address";
import {AdditionalService} from "../../model/additional-service";
import {Room} from "../../model/room";
import {Image} from "../../model/image";
import {HouseService} from "../../service/house.service";
import {AddressService} from "../../service/address.service";
import {RoomService} from "../../service/room.service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {ImageService} from "../../service/image.service";
import {HouseReservationService} from "../../service/house-reservation.service";
import {HouseReservationSlide} from "../../model/house-reservation-slide";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {CalendarDialogHouseComponent} from "./calendar-dialog/calendar-dialog-house.component";
import {HouseReservation} from "../../model/house-reservation";
import { DatePipe } from '@angular/common'
import {MyUserService} from "../../service/my-user.service";
import {MyUser} from "../../model/my-user";
import {AuthentificationService} from "../../auth/authentification/authentification.service";

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
  house: House = new House(0,'', this.address, '', '', 0, false, 0, this.rooms, this.additionalServices, 0, 0);
  courses_slides: HouseReservationSlide[] = new Array<HouseReservationSlide>();
  final_courses: HouseReservation[] = new Array<HouseReservation>();
  isSlideLoaded: boolean = false;
  lat = 0;
  lng = 0;
  freeCancelation: boolean = false;
  duration: number = 0;
  reservedCourses: HouseReservation[] = new Array();
  allCourses: HouseReservation[] = new Array();
  date: Date = new Date();
  // user: MyUser = new MyUser(0, '','','','','','',this.address, '','');
  userFirstNameSearch: string = "";
  userLastNameSearch: string = "";
  userUsernameSearch: string = "";
  userSearch: HouseReservation[] = new Array();

  constructor(public dialog: MatDialog, private _houseService: HouseService, private _addressService: AddressService, private _roomService: RoomService,
              private _additionalServices: AdditionalServicesService, private _imageService: ImageService, private _houseReservationService: HouseReservationService,
              private _router: Router, private _route: ActivatedRoute, public datepipe: DatePipe, private _myUserService: MyUserService, private _authentification: AuthentificationService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.house.id = +this._route.snapshot.paramMap.get('id');
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

  // TODO: obrisati
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
    this._houseService.getHouseById(this.house.id).subscribe(
      (house:House) => {
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

        this._houseReservationService.getAllActionsByHouseId(this.house.id).subscribe(
          (courses_slides: HouseReservationSlide[]) => {
            this.courses_slides = courses_slides
            this.isSlideLoaded = true
          }
        )

        this._houseReservationService.getAllByHouseIdPlane(this.house.id).subscribe(
          (allCourses: HouseReservation[]) => {
            this.allCourses = allCourses

            for (let course of allCourses)
            {
              // dobavljamo sve rezervacije (to su HouseReservations koje nisu available) - spisak/istorija rezervacija
              if (course.available == false && course.availabilityPeriod == false)
              {
                  this._myUserService.findUserByHouseReservationId(course.id).subscribe(
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
    this._router.navigate(['/define-unavailable-period-house', this.house.id])
  }

  showCalendar() {
    const dialogRef = this.dialog.open(CalendarDialogHouseComponent, {
      width: '1500px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  createReservation() {
    this._router.navigate(['/create-reservation-for-client', this.house.id])
  }

  makeReport(id: number) {
    this._router.navigate(['/house-report', id, this.house.id])
  }

  checkDate(endDate: string) {
    this.datepipe.transform(this.date, 'dd/MM/yyyy HH:mm:ss');
    // console.log(Number(endDate))
    // console.log(Number(Date.parse(this.date.toString()).toString()))

    if (Number(endDate) < Number(Date.parse(this.date.toString()).toString()))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  seeGuestProfile(id: number) {
    this._router.navigate(['/guest-profile', id])
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
