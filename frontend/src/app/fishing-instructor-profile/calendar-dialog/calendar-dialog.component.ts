import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { CalendarEvent, CalendarView } from 'angular-calendar';

@Component({
  selector: 'app-calendar-dialog',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './calendar-dialog.component.html',
  styleUrls: ['./calendar-dialog.component.css']
})
export class CalendarDialogComponent implements OnInit {

  view: CalendarView = CalendarView.Month;

  viewDate: Date = new Date();

  events: CalendarEvent[] = [];
  
  constructor(
    public dialogRef: MatDialogRef<CalendarDialogComponent>
  ) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
