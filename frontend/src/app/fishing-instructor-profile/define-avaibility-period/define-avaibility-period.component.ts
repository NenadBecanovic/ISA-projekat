import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-define-avaibility-period',
  templateUrl: './define-avaibility-period.component.html',
  styleUrls: ['./define-avaibility-period.component.css']
})
export class DefineAvaibilityPeriodComponent implements OnInit {

  dateFrom = new FormControl(new Date());
  dateTo = new FormControl(new Date());

  constructor(
    public dialogRef: MatDialogRef<DefineAvaibilityPeriodComponent>
  ) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ok(){
    alert(this.dateFrom);
  }
}
