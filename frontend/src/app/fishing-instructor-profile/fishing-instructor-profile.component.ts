import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Address } from '../model/address';
import { MyUser } from '../model/my-user';
import { CalendarDialogComponent } from './calendar-dialog/calendar-dialog.component';
import { DefineAvaibilityPeriodComponent } from './define-avaibility-period/define-avaibility-period.component';

@Component({
  selector: 'app-fishing-instructor-profile',
  templateUrl: './fishing-instructor-profile.component.html',
  styleUrls: ['./fishing-instructor-profile.component.css']
})
export class FishingInstructorProfileComponent implements OnInit {

  address: Address = new Address("Kotor","Kotor","Crna Gora",0,0,31100)
  instructor: MyUser = new MyUser("Kapetan","Kuka","","","kuka","",this.address, "065454545", "Zelim");
  filterTerm!: string;

  adventures = [{
    "id": 1,
    "name": "Ludilo",
    "address": this.address,
    "persons": "5",
    "price": "30"
  },
  {
    "id": 2,
    "name": "Ludilo",
    "address": this.address,
    "persons": "5",
    "price": "30"
  },
  {
    "id": 3,
    "name": "Ludilo",
    "address": this.address,
    "persons": "5",
    "price": "30"
  },
  {
    "id": 4,
    "name": "gfgf",
    "address": this.address,
    "persons": "5",
    "price": "30"
  },
  {
    "id": 5,
    "name": "Waaa",
    "address": this.address,
    "persons": "5",
    "price": "30"
  },
  {
    "id": 6,
    "name": "Wohoo",
    "address": this.address,
    "persons": "5",
    "price": "30"
  },
  {
    "id": 7,
    "name": "LudAaaailo",
    "address": this.address,
    "persons": "5",
    "price": "30"
  },
  {
    "id": 8,
    "name": "Ludilo",
    "address": this.address,
    "persons": "7",
    "price": "35"
  },
  {
    "id": 9,
    "name": "Haos",
    "address": this.address,
    "persons": "5",
    "price": "30"
  },
  {
    "id": 10,
    "name": "Zezanje",
    "address": this.address,
    "persons": "5",
    "price": "30"
  }
]

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

  defineAvaibilityDialog(){
    const dialogRef = this.dialog.open(DefineAvaibilityPeriodComponent, {
      width: '700px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }
}
