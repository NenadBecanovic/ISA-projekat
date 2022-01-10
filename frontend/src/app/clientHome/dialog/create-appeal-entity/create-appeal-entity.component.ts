import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MyUserService} from "../../../service/my-user.service";
import {AlertService} from "ngx-alerts";
import {Apeal} from "../../../model/apeal";
import {Feedback} from "../../../model/feedback";

@Component({
  selector: 'app-create-appeal-entity',
  templateUrl: './create-appeal-entity.component.html',
  styleUrls: ['./create-appeal-entity.component.css']
})
export class CreateAppealEntityComponent implements OnInit {

  appeal: Apeal = new Apeal();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,  private _myUserService: MyUserService, private alertService:AlertService,
              public dialogRef: MatDialogRef<CreateAppealEntityComponent>) { }

  ngOnInit(): void {
    this.appeal.reservationId = this.data.houseReservationId;
    this.appeal.hasHouse = this.data.isHouse;
    this.appeal.hasBoat = this.data.isBoat;
    this.appeal.hasHouseOwner = this.data.isHouseOwner;
    this.appeal.hasBoatOwner = this.data.isHouseOwner;
    this.appeal.ownerId = this.data.ownerId;
    this.appeal.senderId = this.data.senderId;
    console.log(this.data)
  }

  doTextareaValueChange($event: Event) {
    try {
      // @ts-ignore
      this.appeal.review = $event.target.value;
    } catch(e) {
      console.info('could not set textarea-value');
    }
  }

  ok(){
    console.log(this.appeal)
    this._myUserService.saveApeal(this.appeal).subscribe(   // subscribe - da bismo dobili odgovor beka
      (appeal: Apeal) => {
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
