import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Feedback} from "../../../model/feedback";
import {DeleteRequest} from "../../../model/delete-request";
import {MyUserService} from "../../../service/my-user.service";
import {AlertService} from "ngx-alerts";
import { FeedbackService } from 'src/app/service/feedback.service';

@Component({
  selector: 'app-create-feedback',
  templateUrl: './create-feedback.component.html',
  styleUrls: ['./create-feedback.component.css']
})
export class CreateFeedbackComponent implements OnInit {
  feedback: Feedback = new Feedback();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,  private _feedbackService: FeedbackService, private alertService:AlertService,
              public dialogRef: MatDialogRef<CreateFeedbackComponent>) { }

  ngOnInit(): void {
    this.feedback.reservationId = this.data.houseReservationId;
    this.feedback.hasHouse = this.data.isHouse;
    this.feedback.hasBoat = this.data.isBoat;
    this.feedback.hasHouseOwner = this.data.isHouseOwner;
    this.feedback.hasBoatOwner = this.data.isHouseOwner;
    this.feedback.ownerId = this.data.ownerId;
    console.log(this.data)
  }


  doTextareaValueChange($event: Event) {
    try {
      // @ts-ignore
      this.feedback.review = $event.target.value;
    } catch(e) {
      console.info('could not set textarea-value');
    }
  }

  ok(){
    console.log(this.feedback)
    this._feedbackService.saveFeedback(this.feedback).subscribe(   // subscribe - da bismo dobili odgovor beka
      (feedback: Feedback) => {
        this.dialogRef.close();
        this.alertService.success('Uspjesno poslata revizija');
      },
      (error) => {
        this.alertService.danger('Pokusajte ponovo');
      },
    )
  }

  close(){
    this.dialogRef.close();
  }
}
