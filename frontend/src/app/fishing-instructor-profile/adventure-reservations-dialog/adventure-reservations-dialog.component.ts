import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-adventure-reservations-dialog',
  templateUrl: './adventure-reservations-dialog.component.html',
  styleUrls: ['./adventure-reservations-dialog.component.css']
})
export class AdventureReservationsDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AdventureReservationsDialogComponent>) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
