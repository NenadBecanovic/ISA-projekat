import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminAnswer } from 'src/app/model/admin-answer';
import { MyUserService } from 'src/app/service/my-user.service';
import { DeleteRequestAnswerDialogComponent } from '../delete-request-answer-dialog/delete-request-answer-dialog.component';

@Component({
  selector: 'app-decline-new-user-dialog',
  templateUrl: './decline-new-user-dialog.component.html',
  styleUrls: ['./decline-new-user-dialog.component.css']
})
export class DeclineNewUserDialogComponent implements OnInit {

  userId: number = 0;
  answer: AdminAnswer = new AdminAnswer();

  constructor(public dialogRef: MatDialogRef<DeleteRequestAnswerDialogComponent>, private _myUserService: MyUserService) { }

  ngOnInit() {
    this.answer.clientResponse='';
  }

  deleteUser(){
    this._myUserService.declineNewUserRequest(this.userId, this.answer).subscribe(
      (ok: Boolean) => {
        this.dialogRef.close();
      },
      (error) => {
        // console.log(error)
      }
    )
  }

}
