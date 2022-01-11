import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminAnswer } from 'src/app/model/admin-answer';

@Component({
  selector: 'app-report-answer-dialog',
  templateUrl: './report-answer-dialog.component.html',
  styleUrls: ['./report-answer-dialog.component.css']
})
export class ReportAnswerDialogComponent implements OnInit {

  ownerId: number = 0;
  guestId: number = 0;
  answer: AdminAnswer = new AdminAnswer();

  constructor(public dialogRef: MatDialogRef<ReportAnswerDialogComponent>) { }

  ngOnInit() {
  }

  ok(){
    
  }

}
