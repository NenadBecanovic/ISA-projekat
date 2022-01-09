import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdditionalService } from 'src/app/model/additional-service';

@Component({
  selector: 'app-make-reservation-dialog',
  templateUrl: './make-reservation-dialog.component.html',
  styleUrls: ['./make-reservation-dialog.component.css']
})
export class MakeReservationDialogComponent implements OnInit {

  timeStart!: number;
  date = new Date();
  adventureId!: number;
  adventureLength!: number;
  numberOfPersons!: number;
  additionalServices!: Array<AdditionalService>;

  constructor(public dialogRef: MatDialogRef<MakeReservationDialogComponent>) { 

  }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ok(){
    alert(this.adventureId);
    this.date.setHours(this.timeStart);
    alert(this.date);
  }

}
