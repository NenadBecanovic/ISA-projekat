import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminAnswer } from 'src/app/model/admin-answer';
import { MyUserService } from 'src/app/service/my-user.service';

@Component({
  selector: 'app-delete-request-answer-dialog',
  templateUrl: './delete-request-answer-dialog.component.html',
  styleUrls: ['./delete-request-answer-dialog.component.css']
})
export class DeleteRequestAnswerDialogComponent implements OnInit {

  userId: number = 0;
  answer: AdminAnswer = new AdminAnswer();

  constructor(public dialogRef: MatDialogRef<DeleteRequestAnswerDialogComponent>, private _myUserService: MyUserService) { }

  ngOnInit() {
    this.answer.clientResponse='';
  }

  deleteUser(){
    this._myUserService.deleteUserWithRequest(this.userId, this.answer).subscribe(
      (ok: Boolean) => {
        this.dialogRef.close();
      },
      (error) => {
        // console.log(error)
      }
    )
  }

  declineRequest(){
    this._myUserService.declineDeleteRequest(this.userId, this.answer).subscribe(
      (ok: Boolean) => {
        this.dialogRef.close();
      },
      (error) => {
        // console.log(error)
      }
    )
  }
}
