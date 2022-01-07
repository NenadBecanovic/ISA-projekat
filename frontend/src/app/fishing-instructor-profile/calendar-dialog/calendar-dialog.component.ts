import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { CalendarDayViewBeforeRenderEvent, CalendarEvent, CalendarMonthViewBeforeRenderEvent, CalendarView, CalendarViewPeriod, CalendarWeekViewBeforeRenderEvent } from 'angular-calendar';
import { CalendarEventActionsComponent } from 'angular-calendar/modules/common/calendar-event-actions.component';
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

  events: CalendarEvent[] = [
    {
      title: 'Rezervacija taj korisnik tada i tada,taj dan',
      color: colors.yellow,
      start: new Date(),
    },
    {
      title: 'Rezervacija wohoooo',
      color: colors.yellow,
      start: new Date("2022-1-"+7)
    },
  ];

  period!: CalendarViewPeriod;
  constructor(public dialogRef: MatDialogRef<CalendarDialogComponent>,private cdr: ChangeDetectorRef) { 
    
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
  
  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
