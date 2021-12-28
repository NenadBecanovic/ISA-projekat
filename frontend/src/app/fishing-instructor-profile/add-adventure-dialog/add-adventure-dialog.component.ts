import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-adventure-dialog',
  templateUrl: './add-adventure-dialog.component.html',
  styleUrls: ['./add-adventure-dialog.component.css']
})
export class AddAdventureDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AddAdventureDialogComponent>
  ) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
