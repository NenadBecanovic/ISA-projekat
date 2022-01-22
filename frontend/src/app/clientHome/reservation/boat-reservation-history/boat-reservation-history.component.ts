import { Component, OnInit } from '@angular/core';
import {Address} from "../../../model/address";
import {MyUser} from "../../../model/my-user";
import {HouseReservation} from "../../../model/house-reservation";
import {AuthentificationService} from "../../../auth/authentification/authentification.service";
import {HouseReservationService} from "../../../service/house-reservation.service";
import {MyUserService} from "../../../service/my-user.service";
import {MatDialog} from "@angular/material/dialog";
import {CreateFeedbackComponent} from "../../dialog/create-feedback/create-feedback.component";
import {CreateAppealEntityComponent} from "../../dialog/create-appeal-entity/create-appeal-entity.component";
import {BoatReservationService} from "../../../service/boat-reservation.service";
import {BoatReservation} from "../../../model/boat-reservation";

@Component({
  selector: 'app-boat-reservation-history',
  templateUrl: './boat-reservation-history.component.html',
  styleUrls: ['./boat-reservation-history.component.css']
})
export class BoatReservationHistoryComponent implements OnInit {
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  displayedColumns: string[] = ['Datum pocetka', 'Datum kraja', 'Naziv vikendice', 'Naziv vlasnika', 'Broj gostiju', 'Ukupna cena',
    'Ocena entitet', 'Ocena vlasnik', 'Zalba entitet', 'Zalba vlasnik', 'Otkazano'];
  dataSource: BoatReservation[] = new Array();
  owner: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  currentDate: Date = new Date();

  constructor(private _authentification: AuthentificationService, private _boatReservation: BoatReservationService,
              private _myUserService: MyUserService, public dialog: MatDialog) { }



  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._boatReservation.getBoatReservationsByGuestId(this.user.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (reservations: BoatReservation[]) => {
            this.dataSource = reservations
            this.dataSource = this.dataSource.filter(s => s.milisEndDate < this.currentDate.getTime() || s.cancelled == true)
            for(let res of this.dataSource){
              this._myUserService.findUserByBoatId(res.boatId).subscribe(
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

  createFeedback(id: number, ownerId: number) {
    const dialogRef = this.dialog.open(CreateFeedbackComponent,{
      width: '400px',
      data: {
        houseReservationId: id,
        isHouse : false,
        isHouseOwner : false,
        isBoat : true,
        isBoatOwner : false,
        isInstructor : false,
        ownerId: ownerId
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadData()
    });
  }

  createFeedbackOwner(id: number, ownerId: number) {
    const dialogRef = this.dialog.open(CreateFeedbackComponent,{
      width: '400px',
      data: {
        houseReservationId: id,
        isHouse : false,
        isHouseOwner : false,
        isBoat : false,
        isBoatOwner : true,
        isInstructor : false,
        ownerId: ownerId
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadData()
    });
  }

  createAppealEntity(id: number, ownerId: number) {
    const dialogRef = this.dialog.open(CreateAppealEntityComponent,{
      width: '400px',
      data: {
        houseReservationId: id,
        isHouse : false,
        isHouseOwner : false,
        isBoat : true,
        isBoatOwner : false,
        isInstructor : false,
        ownerId: ownerId,
        senderId: this.user.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadData()
    });
  }

  createAppealOwner(id: number, ownerId: number) {
    const dialogRef = this.dialog.open(CreateAppealEntityComponent,{
      width: '400px',
      data: {
        houseReservationId: id,
        isHouse : false,
        isHouseOwner : false,
        isBoat : false,
        isBoatOwner : true,
        isInstructor : false,
        ownerId: ownerId,
        senderId: this.user.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadData()
    });
  }
}
