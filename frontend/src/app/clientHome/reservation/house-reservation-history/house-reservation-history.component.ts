import { Component, OnInit } from '@angular/core';
import {MyUser} from "../../../model/my-user";
import {AuthentificationService} from "../../../auth/authentification/authentification.service";
import {HouseReservationService} from "../../../service/house-reservation.service";
import {Address} from "../../../model/address";
import {HouseReservation} from "../../../model/house-reservation";
import {MyUserService} from "../../../service/my-user.service";
import {CreateFeedbackComponent} from "../../dialog/create-feedback/create-feedback.component";
import {CreateAppealEntityComponent} from "../../dialog/create-appeal-entity/create-appeal-entity.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-house-reservation-history',
  templateUrl: './house-reservation-history.component.html',
  styleUrls: ['./house-reservation-history.component.css']
})
export class HouseReservationHistoryComponent implements OnInit {
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  displayedColumns: string[] = ['Datum pocetka', 'Datum kraja', 'Naziv vikendice', 'Naziv vlasnika', 'Broj gostiju', 'Ukupna cena',
  'Ocena entitet', 'Ocena vlasnik', 'Zalba entitet', 'Zalba vlasnik'];
  dataSource: HouseReservation[] = new Array();
  owner: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  currentDate: Date = new Date();

  constructor(private _authentification: AuthentificationService, private _houseReservation: HouseReservationService,
              private _myUserService: MyUserService, public dialog: MatDialog) { }



  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._houseReservation.getHouseReservationsByGuestId(this.user.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (reservations: HouseReservation[]) => {
            console.log(reservations)
            this.dataSource = reservations
            for(let res of this.dataSource){
              this._myUserService.findUserByHouseid(res.houseId).subscribe(
                (owner:MyUser) => {
                  res.houserOwnerName = owner.firstName + ' ' + owner.lastName
                  res.ownerId = owner.id;
                },(error => {

                })

              )
            }

            // this.dataSource = this.dataSource.filter(s => s.milisEndDate < this.currentDate.getTime()
            // )

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
        isHouse : true,
        isHouseOwner : false,
        isBoat : false,
        isBoatOwner : false,
        isInstructor : false,
        ownerId: ownerId
      },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  createFeedbackOwner(id: number, ownerId: number) {
    const dialogRef = this.dialog.open(CreateFeedbackComponent,{
      width: '400px',
      data: {
        houseReservationId: id,
        isHouse : false,
        isHouseOwner : true,
        isBoat : false,
        isBoatOwner : false,
        isInstructor : false,
        ownerId: ownerId
      },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  createAppealEntity(id: number, ownerId: number) {
    const dialogRef = this.dialog.open(CreateAppealEntityComponent,{
      width: '400px',
      data: {
        houseReservationId: id,
        isHouse : true,
        isHouseOwner : false,
        isBoat : false,
        isBoatOwner : false,
        isInstructor : false,
        ownerId: ownerId,
        senderId: this.user.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  createAppealOwner(id: number, ownerId: number) {
    const dialogRef = this.dialog.open(CreateAppealEntityComponent,{
      width: '400px',
      data: {
        houseReservationId: id,
        isHouse : false,
        isHouseOwner : true,
        isBoat : false,
        isBoatOwner : false,
        isInstructor : false,
        ownerId: ownerId,
        senderId: this.user.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }
}
