import { Component, OnInit } from '@angular/core';
import {House} from "../model/house";
import {Address} from "../model/address";
import {AdditionalService} from "../model/additional-service";
import {Room} from "../model/room";
import {A} from "@angular/cdk/keycodes";
import {HouseReservation} from "../model/house-reservation";
import {Image} from "../model/image";
import {HouseService} from "../service/house.service";
import {AddressService} from "../service/address.service";

@Component({
  selector: 'app-house-profile-for-house-owner',
  templateUrl: './house-profile-for-house-owner.component.html',
  styleUrls: ['./house-profile-for-house-owner.component.css']
})
export class HouseProfileForHouseOwnerComponent implements OnInit {
  slides = [{'image': 'assets/house1.jpg'}, {'image': 'assets/house2.jpg'}, {'image': 'assets/house3.jpg'}]
  address: Address = new Address(0,"","","",0,0,31100)
  images: Image[] = new Array<Image>()
  house: House = new House('', this.address, '', '', 0, false, 0, this.images);

  // service1: AdditionalService= new AdditionalService("DJAKUZI", 2000);
  // service2: AdditionalService= new AdditionalService("BAZEN", 3000);
  // additionalServices: Array<AdditionalService>;
 // // @ts-ignore
  // room1: Room = new Room(3, this.house);
  // // @ts-ignore
  // room2: Room = new Room(2, this.house);
  // courses: Array<HouseReservation> = new Array<HouseReservation>();
  // houseReservation1: HouseReservation;
  // houseReservation2: HouseReservation;
  // houseReservation3: HouseReservation;
  // houseReservation4: HouseReservation;
  // image1: Image = new Image('');
  // images: Array<Image> = new Array<Image>();

  constructor(private _houseService: HouseService, private  _addressService: AddressService) {
    // this.additionalServices = new Array<AdditionalService>();
    // this.additionalServices.push(this.service1);
    // this.additionalServices.push(this.service2);
    // this.rooms = new Array<Room>();
    // this.rooms.push(this.room1);
    // this.rooms.push(this.room2);
    //
    // this.images.push(this.image1);
    //
    // this.house = new House("Vikendica na Tari", this.address, "Najtrazenija vikendica na Tari! Tara se nalazi u zapadnom delu Srbije i pripada unutrašnjem pojasu Dinarida. Prosečna nadmorska visina planine Tare je 1200 m. Najviši vrh je Kozji rid sa 1591 m. Pripada grupi podrinjskih planina i ima složenu geološku prošlost i raznoliku petrografsku građu.\n" +
    //   "Položaj planine Tare predstavlja povoljan uslov za razvoj turizma, jer omogućava raznovrstan sadržaj turističkog boravka, odnosno posetu većem broju turističkih lokaliteta. Po bogatstvu i raznovrsnosti turističih vrednosti, ekološki očuvana prirodna sredina, planinski reljef sa umerenom nadmorskom visinom od 800-1500 m, pogodni tereni za zimske sportove, bogat i raznovrstan " +
    //   "biljni i životinjski svet, blagotvorna klima, Drina sa veštačkim jezerima Zaovine i Perućac, daju ovoj planini veliku turističku vrednost što je čini konkurentnom u odnosu na Zlatibor i druge planine. Tara ima povoljan položaj prema komplementarnim turističkim vrednostima u neposrednoj okolini: Užicu, Mokroj Gori, Višegradu sa brojnim kulturno-istorijskim spomenicima, koji privlače " +
    //   "izletnička kretanja turista stacioniranih na Tari u cilju zadovoljenja obrazovno-kulturnih potreba i bogatijeg sadržaja i boravka.", "Zabranjeno pusenje.", 20000, false, 5000, this.rooms, this.additionalServices, this.courses, this.images);
    //
    // this.houseReservation1 = new HouseReservation(new Date("Dec 19 2021 07:44:57"), new Date("Dec 20 2021 07:44:57"), 5, "Bazen", 20000, true, this.house)
    // this.houseReservation2 = new HouseReservation(new Date("Dec 21 2021 07:44:57"), new Date("Dec 22 2021 07:44:57"), 3, "Rostilj", 10000, true, this.house)
    // this.houseReservation3 = new HouseReservation(new Date("Dec 23 2021 07:44:57"), new Date("Dec 25 2021 07:44:57"), 6, "Klizaliste", 10000, true, this.house)
    // this.houseReservation4 = new HouseReservation(new Date("Dec 28 2021 07:44:57"), new Date("Dec 30 2021 07:44:57"), 2, "Djakuzi", 10000, true, this.house)
    //
    // this.courses.push(this.houseReservation1);
    // this.courses.push(this.houseReservation2);
    // this.courses.push(this.houseReservation3);
    // //this.courses.push(this.houseReservation4);
    //
    // this.house.courses = this.courses;
  }

  ngOnInit(): void {
    this.loadData();

    // this.slides.append("assets/cottage.jpg")
    // this.slides.append("assets/cottage.jpg")
    // this.slides.append("assets/cottage.jpg")
    // this.slides.append("assets/cottage.jpg")
    // this.slides.append("assets/cottage.jpg")
  }

  // https://www.eduforbetterment.com/file-upload-using-material-components-in-angular/
  addImageToHouse(e: any) {
    const file: File = e.files[0];
    const reader = new FileReader();
  }

  showMap() {

  }

  addActionDialog() {

  }

  loadData() { // ucitavanje iz baze
      this._houseService.getHouseById(1).subscribe(
        (house:House) => {
          this.house = house
          console.log(this.house)

          this.address = this.house.address;
          console.log(this.house.address)

          // this._addressService.getAddressById(this.house.addressDTO.id).subscribe(
          //   (address:Address) => {
          //     this.address = address
          //   }
          // )
        }
      )
  }
}
