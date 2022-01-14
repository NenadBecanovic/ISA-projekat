import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Address} from "../../../model/address";
import {MyUser} from "../../../model/my-user";
import {BoatReservation} from "../../../model/boat-reservation";
import {AuthentificationService} from "../../../auth/authentification/authentification.service";
import {BoatReservationService} from "../../../service/boat-reservation.service";
import {MyUserService} from "../../../service/my-user.service";
import {MatDialog} from "@angular/material/dialog";
import {CreateFeedbackComponent} from "../../dialog/create-feedback/create-feedback.component";
import {CreateAppealEntityComponent} from "../../dialog/create-appeal-entity/create-appeal-entity.component";
import {MatSelectChange} from "@angular/material/select";
import { Inject }  from '@angular/core';
import { DOCUMENT } from '@angular/common';
import {HouseReservation} from "../../../model/house-reservation";
import {HouseReservationService} from "../../../service/house-reservation.service";
import {CancelReservation} from "../../../model/cancel-reservation";
import {Subscription} from "../../../model/subscription";
import {BoatService} from "../../../service/boat.service";
import {Boat} from "../../../model/boat";
import {HouseService} from "../../../service/house.service";
import {House} from "../../../model/house";
import {AdventureReservation} from "../../../model/adventure-reservation";
import {AdventureReservationService} from "../../../service/adventure-reservation.service";

@Component({
  selector: 'app-future-reservation',
  templateUrl: './future-reservation.component.html',
  styleUrls: ['./future-reservation.component.css']
})
export class FutureReservationComponent implements OnInit {
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  displayedColumns: string[] = ['Datum pocetka', 'Datum kraja', 'Naziv vikendice', 'Naziv vlasnika', 'Broj gostiju', 'Ukupna cena',
    'Operacije'];
  dataSource: any[] = new Array();
  cancelSource: any[] = new Array();
  owner: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  currentDate: Date = new Date();
  selctedEntity: string = 'Vikendica'
  @ViewChild('myTable') myTable!: ElementRef;
  entityName: string = 'Naziv vikendice'
  cancelReservationClass: CancelReservation = new CancelReservation();

  constructor( @Inject(DOCUMENT) document: any, private _authentification: AuthentificationService, private _boatReservation: BoatReservationService,
              private _myUserService: MyUserService, public dialog: MatDialog, private _houseReservation: HouseReservationService,
               private _boatService: BoatService, private _houseService: HouseService, private _adventureReservation: AdventureReservationService) { }




  ngOnInit(): void {
    this.loadHouseData();
  }

  loadData() {
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._boatReservation.getBoatReservationsByGuestId(this.user.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (reservations: BoatReservation[]) => {
            console.log(reservations)
            this.dataSource = reservations
            this.dataSource = this.dataSource.filter(s => s.milisEndDate >= this.currentDate.getTime() && s.cancelled == false)
            for(let res of this.dataSource){
              this._myUserService.findUserByBoatId(res.boatId).subscribe(
                (owner:MyUser) => {
                  res.houserOwnerName = owner.firstName + ' ' + owner.lastName
                  res.ownerId = owner.id;
                },(error => {

                })
              )
              // res.
              // if()

            }



          },
          (error) => {

          },
        )
      },
      (error) => {
      },
    )
    console.log(this.currentDate.getTime())
    console.log(this.currentDate)

  }


  loadHouseData() {
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._houseReservation.getHouseReservationsByGuestId(this.user.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (reservations: HouseReservation[]) => {
            console.log(reservations)
            this.dataSource = reservations
            this.dataSource = this.dataSource.filter(s => s.milisEndDate >= this.currentDate.getTime() && s.cancelled == false)
            for(let res of this.dataSource){
              this._myUserService.findUserByHouseid(res.houseId).subscribe(
                (owner:MyUser) => {
                  res.houserOwnerName = owner.firstName + ' ' + owner.lastName
                  res.ownerId = owner.id;
                },(error => {

                })

              )
            }
          },
          (error) => {

          },
        )
      },
      (error) => {
      },
    )
    console.log(this.currentDate.getTime())
    console.log(this.currentDate)

  }


  changeSelection($event: MatSelectChange) {
    if(this.selctedEntity === 'Vikendica'){
      this.entityName = 'Naziv vikendice'
      this.loadHouseData()
    }else if(this.selctedEntity ==='Brodovi'){
      this.entityName = 'Naziv broda'
      this.loadData()
    }else if(this.selctedEntity === 'Avanture'){
      this.entityName = 'Naziv avanture'
      this.loadAdventureData()
    }
  }


  loadAdventureData() {
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._adventureReservation.getAdventureReservationsByGuestId(this.user.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (reservations: AdventureReservation[]) => {
            this.dataSource = reservations
            this.dataSource = this.dataSource.filter(s => s.milisEndDate >= this.currentDate.getTime() && s.cancelled == false)
            for(let res of this.dataSource){
              this._myUserService.findUserByAdventureId(res.adventureId).subscribe(
                (owner:MyUser) => {
                  res.houserOwnerName = owner.firstName + ' ' + owner.lastName
                  res.ownerId = owner.id;
                },(error => {

                })

              )
            }

          },
          (error) => {

          },
        )
      },
      (error) => {
      },
    )
    console.log(this.currentDate.getTime())
    console.log(this.currentDate)

  }

  async cancelReservation(id: number) {
    this.cancelReservationClass.reservationId = id
    this.cancelReservationClass.guestId = this.user.id;
    var cancelFee = 0
    if (this.selctedEntity === 'Vikendica') {
      this.cancelReservationClass.isHouse = true;
      this.cancelSource = this.dataSource.filter(s => s.id == id)
      var house = this.cancelSource[0].houseId
      this._houseService.getHouseById(house).subscribe(
        (house: House) => {
          cancelFee = house.cancalletionFee;
        }, (error => {
        })
      )

    } else if (this.selctedEntity === 'Brodovi') {
      this.cancelReservationClass.isBoat = true;
      this.cancelSource = this.dataSource.filter(s => s.id == id)
      console.log(this.cancelSource)
      var boat = this.cancelSource[0].boatId
      this._boatService.getBoatById(boat).subscribe(
        (boat: Boat) => {
          cancelFee = boat.cancalletionFee;
        }, (error => {
        })
      )

    } else if (this.selctedEntity === 'Avanture') {
      this.cancelReservationClass.isAdventure = true;
    }


    setTimeout(()=>{if (confirm("Da li ste sigurni da zelite da otkazete rezervaciju? Bice vam naplaceno " + cancelFee + " dinara .")) {
      this._myUserService.cancelReservation(this.cancelReservationClass).subscribe(
        (cancelRes: CancelReservation) => {
          if(this.selctedEntity === 'Vikendica'){
            this.entityName = 'Naziv vikendice'
            this.loadHouseData()
          }else if(this.selctedEntity ==='Brodovi'){
            this.entityName = 'Naziv broda'
            this.loadData()
          }else if(this.selctedEntity === 'Avanture'){
            this.entityName = 'Naziv avanture'
            this.loadAdventureData()
          }
        }
      )
    }}, 700)
  }
}
