import { Component, OnInit } from '@angular/core';
import {CalendarEvent, CalendarView} from "angular-calendar";
import {HouseReservation} from "../../../model/house-reservation";
import {BoatReservation} from "../../../model/boat-reservation";
import {MatDialogRef} from "@angular/material/dialog";
import {HouseReservationService} from "../../../service/house-reservation.service";
import {BoatReservationService} from "../../../service/boat-reservation.service";

@Component({
  selector: 'app-calendar-dialog-boat',
  templateUrl: './calendar-dialog-boat.component.html',
  styleUrls: ['./calendar-dialog-boat.component.css']
})
export class CalendarDialogBoatComponent implements OnInit {

  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  courses: BoatReservation[] = new Array();

  constructor(public dialogRef: MatDialogRef<CalendarDialogBoatComponent>, private _boatReservationService: BoatReservationService) { }

  ngOnInit(): void {
    this.loadData();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  private loadData() {
  }
}
