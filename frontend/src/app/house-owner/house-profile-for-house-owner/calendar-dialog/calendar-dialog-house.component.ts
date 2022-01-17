import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { CalendarDayViewBeforeRenderEvent, CalendarEvent, CalendarMonthViewBeforeRenderEvent, CalendarView, CalendarViewPeriod, CalendarWeekViewBeforeRenderEvent } from 'angular-calendar';
import {MatDialogRef} from "@angular/material/dialog";
import {HouseReservation} from "../../../model/house-reservation";
import {HouseReservationService} from "../../../service/house-reservation.service";
import { colors } from 'src/app/fishing-instructor-profile/calendar-dialog/demo-utils/colors';
import { Router } from '@angular/router';

@Component({
  selector: 'app-calendar-dialog',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './calendar-dialog-house.component.html',
  styleUrls: ['./calendar-dialog-house.component.css']
})
export class CalendarDialogHouseComponent implements OnInit {

  view: CalendarView = CalendarView.Month;

  viewDate: Date = new Date();
  allReservations: HouseReservation[] = new Array<HouseReservation>();
  events: CalendarEvent[] = [];

  period!: CalendarViewPeriod;
  constructor(public dialogRef: MatDialogRef<CalendarDialogHouseComponent>,private cdr: ChangeDetectorRef, private _router: Router) { 
    
  }

  beforeViewRender(
    event:
      | CalendarMonthViewBeforeRenderEvent
      | CalendarWeekViewBeforeRenderEvent
      | CalendarDayViewBeforeRenderEvent
  ) {
    this.period = event.period;
    this.cdr.detectChanges();
  }

  changeDay(date: Date) {
    this.viewDate = date;
    this.view = CalendarView.Day;
  }
  
  ngOnInit() {
    for(let reservation of this.allReservations){
      if(reservation.action && reservation.available){
        this.events.push({
          title: 'Akcija' ,
          color: colors.blue,
          start: new Date(Number(reservation.startDate)),
          end: new Date(Number(reservation.endDate)),
          id: 0
        });
      }else if(reservation.availabilityPeriod){
        this.events.push({
          title: 'Odmor',
          color: colors.purple,
          start: new Date(Number(reservation.startDate)),
          end: new Date(Number(reservation.endDate)),
          id: 0
        });
      }else{
        this.events.push({
          title: 'Korisnik: ' + reservation.guest.firstName + ' ' + reservation.guest.lastName ,
          color: colors.yellow,
          start: new Date(Number(reservation.startDate)),
          end: new Date(Number(reservation.endDate)),
          id: reservation.guest.id
        });
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  eventClicked({ event }: { event: CalendarEvent }): void {
    if(event.id != 0){
      this._router.navigate(['/guest-profile', event.id])
    }
  }

}
