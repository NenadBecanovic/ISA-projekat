import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import { CalendarEvent, CalendarView } from 'angular-calendar';
import {MatDialogRef} from "@angular/material/dialog";
import {HouseReservation} from "../../../model/house-reservation";
import {HouseReservationService} from "../../../service/house-reservation.service";

@Component({
  selector: 'app-calendar-dialog',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './calendar-dialog-house.component.html',
  styleUrls: ['./calendar-dialog-house.component.css']
})
export class CalendarDialogHouseComponent implements OnInit {

  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  courses: HouseReservation[] = new Array();

  constructor(public dialogRef: MatDialogRef<CalendarDialogHouseComponent>, private _houseReservationService: HouseReservationService) { }

  ngOnInit() {
    this.loadData();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  private loadData() {
  }

}
