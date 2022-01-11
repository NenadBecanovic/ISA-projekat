import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { AdventureReservation } from 'src/app/model/adventure-reservation';
import { UserInfo } from 'src/app/model/user-info';
import { Report } from 'src/app/model/report';
import { AdventureReservationService } from 'src/app/service/adventure-reservation.service';
import { MyUserService } from 'src/app/service/my-user.service';
import { ReportService } from 'src/app/service/report.service';
import { ActivatedRoute, Router } from '@angular/router';

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
  report: Report = new Report(0, '', false, false, 0, 0, 0);

  constructor(public dialogRef: MatDialogRef<AdventureReservationsDialogComponent>, private _adventureReservationService: AdventureReservationService, private _myUserService: MyUserService,
        private _reportService: ReportService, private _alertService: AlertService, private _router: Router, private _route: ActivatedRoute) { }

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
        var today = Number(new Date());
        for (let reservation of allReservations)
        {
          if(Number(reservation.endDate) < today){
            reservation.hasReport = true;
          }
          if (reservation.isAvailable == false && reservation.availabilityPeriod == false)
          {
            this._myUserService.findUserByFishingAdventureReservationId(reservation.guestId).subscribe(
              (user: UserInfo) => {
                    reservation.guest = user
                  }
                )
          }
        }
      }
    )
  }

  addReport(reservation: AdventureReservation){
    this.report.adventureReservationId = reservation.id;
    this._reportService.save(this.report).subscribe(
      (report: Report) => {
        this.showMakeReport = !this.showMakeReport;
        reservation.hasReport = true;
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      }
    )
  }

  visitUserProfile(guestId: number){
    this._router.navigate(['/guest-profile', guestId])
  }
}
