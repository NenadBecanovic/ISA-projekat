import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { TimePeriod } from 'src/app/model/time-period';
import { AdventureReservationService } from 'src/app/service/adventure-reservation.service';
import { BoatReservationService } from 'src/app/service/boat-reservation.service';
import { CompanyService } from 'src/app/service/company.service';
import { HouseReservationService } from 'src/app/service/house-reservation.service';

@Component({
  selector: 'app-company-profit-dialog',
  templateUrl: './company-profit-dialog.component.html',
  styleUrls: ['./company-profit-dialog.component.css']
})
export class CompanyProfitDialogComponent implements OnInit {

  timePeriod: TimePeriod = new TimePeriod();
  profit: number = 0;
  showProfit: boolean = false;

  constructor(public dialogRef: MatDialogRef<CompanyProfitDialogComponent>, private _adventureReservationService: AdventureReservationService, private _boatReservationService: BoatReservationService,
        private _houseReservationService: HouseReservationService, private _alertService: AlertService) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  calculateProfit() {

    var startDate = Date.parse(this.timePeriod.startDate)   // parsiranje datuma pocetka u milisekunde
    var date =  new Date(startDate)

    var endingDate = Date.parse(this.timePeriod.endDate)   // parsiranje datuma pocetka u milisekunde
    var endDate =  new Date(endingDate)

    if(startDate > endingDate){
      this._alertService.info('Datum kraja ospega mora biti nakon datuma početka!')
    } else{
      this.timePeriod.startDate = Date.parse(date.toString()).toString()
      this.timePeriod.endDate = Date.parse(endDate.toString()).toString()
      this._adventureReservationService.calculatePeriodProfit(this.timePeriod).subscribe(   // subscribe - da bismo dobili odgovor beka
        (profit: number) => {
          this.profit = this.profit + profit;
        },
        (error) => {
          this._alertService.warning("Došlo je do greške!")
        },
      )

      this._boatReservationService.calculatePeriodProfit(this.timePeriod).subscribe(   // subscribe - da bismo dobili odgovor beka
        (profit: number) => {
          this.profit = this.profit + profit;
        },
        (error) => {
          this._alertService.warning("Došlo je do greške!")
        },
      )

      this._houseReservationService.calculatePeriodProfit(this.timePeriod).subscribe(   // subscribe - da bismo dobili odgovor beka
        (profit: number) => {
          this.profit = this.profit + profit;
        },
        (error) => {
          this._alertService.warning("Došlo je do greške!")
        },
      )
      this.showProfit = true;
    }
  }
}
