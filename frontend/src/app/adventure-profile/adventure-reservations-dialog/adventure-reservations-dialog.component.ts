import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdventureReservation } from 'src/app/model/adventure-reservation';
import { AdventureUserInfo } from 'src/app/model/adventure-user-info';
import { Report } from 'src/app/model/report';
import { AdventureReservationService } from 'src/app/service/adventure-reservation.service';
import { MyUserService } from 'src/app/service/my-user.service';

@Component({
  selector: 'app-adventure-reservations-dialog',
  templateUrl: './adventure-reservations-dialog.component.html',
  styleUrls: ['./adventure-reservations-dialog.component.css']
})
export class AdventureReservationsDialogComponent implements OnInit {

  adventureId!: number;
  allReservations: AdventureReservation[] = new Array<AdventureReservation>();
  showMakeReport: Boolean = true;
  reportAdventureReservationId: number = 0;
  reportAdventureId: number = 0;
  report: Report = new Report(0, '', false, false, 0, 0, 0)

  constructor(public dialogRef: MatDialogRef<AdventureReservationsDialogComponent>, private _adventureReservationService: AdventureReservationService, private _myUserService: MyUserService) { }

  ngOnInit() {
    this.onLoad();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  makeReport(){
    this.showMakeReport = !this.showMakeReport;
  }

  onLoad(){
    this._adventureReservationService.getAllActionsByFishingAdventureId(this.adventureId).subscribe(
      (allReservations: AdventureReservation[]) => {
        this.allReservations = allReservations

        for (let reservation of allReservations)
        {
          // dobavljamo sve rezervacije (to su HouseReservations koje nisu available) - spisak/istorija rezervacija
          if (reservation.isAvailable == false && reservation.availabilityPeriod == false)
          {
            this._myUserService.findUserByFishingAdventureReservationId(reservation.guestId).subscribe(
              (user: AdventureUserInfo) => {
                    reservation.guest = user
                  }
                )
          }
        }
      }
    )
  }

  addReport(){
    
  }
}
