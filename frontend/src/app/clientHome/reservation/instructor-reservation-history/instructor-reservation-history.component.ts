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
import {AdventureReservationService} from "../../../service/adventure-reservation.service";
import {AdventureReservation} from "../../../model/adventure-reservation";

@Component({
  selector: 'app-instructor-reservation-history',
  templateUrl: './instructor-reservation-history.component.html',
  styleUrls: ['./instructor-reservation-history.component.css']
})
export class InstructorReservationHistoryComponent implements OnInit {
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  displayedColumns: string[] = ['Datum pocetka', 'Datum kraja', 'Naziv vikendice', 'Naziv vlasnika', 'Broj gostiju', 'Ukupna cena',
     'Ocena vlasnik', 'Zalba vlasnik', 'Otkazano'];
  dataSource: AdventureReservation[] = new Array();
  owner: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  currentDate: Date = new Date();

  constructor(private _authentification: AuthentificationService, private _advetureReservation: AdventureReservationService,
              private _myUserService: MyUserService, public dialog: MatDialog) { }



  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._advetureReservation.getAdventureReservationsByGuestId(this.user.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (reservations: AdventureReservation[]) => {
            this.dataSource = reservations
            this.dataSource = this.dataSource.filter(s => s.milisEndDate < this.currentDate.getTime() || s.cancelled == true)
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

  createFeedbackOwner(id: number, ownerId: number) {
    const dialogRef = this.dialog.open(CreateFeedbackComponent,{
      width: '400px',
      data: {
        houseReservationId: id,
        isHouse : false,
        isHouseOwner : false,
        isBoat : false,
        isBoatOwner : false,
        isInstructor : true,
        ownerId: ownerId
      },
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
        isBoatOwner : false,
        isInstructor : true,
        ownerId: ownerId,
        senderId: this.user.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadData()
    });
  }

}
