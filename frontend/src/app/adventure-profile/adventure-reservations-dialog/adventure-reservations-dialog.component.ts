import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdventureReservation } from 'src/app/model/adventure-reservation';
import { AdventureUserInfo } from 'src/app/model/adventure-user-info';
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

  constructor(public dialogRef: MatDialogRef<AdventureReservationsDialogComponent>, private _adventureReservationService: AdventureReservationService, private _myUserService: MyUserService) { }

  ngOnInit() {
    this.onLoad();
  }

  onNoClick(): void {
    this.dialogRef.close();
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
}
