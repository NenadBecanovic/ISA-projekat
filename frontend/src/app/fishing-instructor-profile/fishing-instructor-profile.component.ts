import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Address } from '../model/address';
import { MyUser } from '../model/my-user';
import { CalendarDialogComponent } from './calendar-dialog/calendar-dialog.component';

@Component({
  selector: 'app-fishing-instructor-profile',
  templateUrl: './fishing-instructor-profile.component.html',
  styleUrls: ['./fishing-instructor-profile.component.css']
})
export class FishingInstructorProfileComponent implements OnInit {

  address: Address = new Address("Kotor","Kotor","Crna Gora",0,0,31100)
  instructor: MyUser = new MyUser("Kapetan","Kuka","","","kuka","",this.address, "065454545", "Zelim");

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  addAdventure(){
    alert("AVANTURA");
  }

  showCalendarDialog(){
    const dialogRef = this.dialog.open(CalendarDialogComponent, {
      width: '1500px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }
}
