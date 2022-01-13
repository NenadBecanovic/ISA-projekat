import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CalendarDayViewBeforeRenderEvent, CalendarEvent, CalendarMonthViewBeforeRenderEvent, CalendarView, CalendarViewPeriod, CalendarWeekViewBeforeRenderEvent } from 'angular-calendar';
import { CalendarEventActionsComponent } from 'angular-calendar/modules/common/calendar-event-actions.component';
import { AdditionalService } from 'src/app/model/additional-service';
import { AdventureReservation } from 'src/app/model/adventure-reservation';
import { UserInfo } from 'src/app/model/user-info';
import { colors } from './demo-utils/colors';

@Component({
  selector: 'app-calendar-dialog',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './calendar-dialog.component.html',
  styleUrls: ['./calendar-dialog.component.css']
})
export class CalendarDialogComponent implements OnInit {

  view: CalendarView = CalendarView.Month;

  viewDate: Date = new Date();
  allReservations: AdventureReservation[] = new Array<AdventureReservation>();
  allActions: AdventureReservation[] = new Array<AdventureReservation>();
  allAvaibilityPeriods: AdventureReservation[] = new Array<AdventureReservation>();
  events: CalendarEvent[] = [];

  period!: CalendarViewPeriod;
  constructor(public dialogRef: MatDialogRef<CalendarDialogComponent>,private cdr: ChangeDetectorRef, private _router: Router) { 
    
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
      this.events.push({
        title: 'Korisnik: ' + reservation.guest.firstName + ' ' + reservation.guest.lastName ,
        color: colors.yellow,
        start: new Date(Number(reservation.startDate)),
        end: new Date(Number(reservation.endDate)),
        id: reservation.guest.id
      });
    }
    for(let action of this.allActions){
      this.events.push({
        title: 'Akcija' ,
        color: colors.blue,
        start: new Date(Number(action.startDate)),
        end: new Date(Number(action.endDate)),
        id: 0
      });
    }
    for(let availabilityPeriod of this.allAvaibilityPeriods){
      this.events.push({
        title: 'Odmor',
        color: colors.purple,
        start: new Date(Number(availabilityPeriod.startDate)),
        end: new Date(Number(availabilityPeriod.endDate)),
        id: 0
      });
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
