import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminAnswer } from 'src/app/model/admin-answer';

@Component({
  selector: 'app-appeal-answer-dialog',
  templateUrl: './appeal-answer-dialog.component.html',
  styleUrls: ['./appeal-answer-dialog.component.css']
})
export class AppealAnswerDialogComponent implements OnInit {

  ownerId: number = 0;
  guestId: number = 0;
  answer: AdminAnswer = new AdminAnswer();

  constructor(public dialogRef: MatDialogRef<AppealAnswerDialogComponent>) { }

  ngOnInit() {
  }

  ok(){
    
  }

}
