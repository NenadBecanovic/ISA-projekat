import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminAnswer } from 'src/app/model/admin-answer';

@Component({
  selector: 'app-delete-request-answer-dialog',
  templateUrl: './delete-request-answer-dialog.component.html',
  styleUrls: ['./delete-request-answer-dialog.component.css']
})
export class DeleteRequestAnswerDialogComponent implements OnInit {

  userId: number = 0;
  answer: AdminAnswer = new AdminAnswer();

  constructor(public dialogRef: MatDialogRef<DeleteRequestAnswerDialogComponent>) { }

  ngOnInit() {
  }

  ok(){
    
  }
}
