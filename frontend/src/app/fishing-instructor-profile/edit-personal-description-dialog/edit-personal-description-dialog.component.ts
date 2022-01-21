import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { MyUserService } from 'src/app/service/my-user.service';

@Component({
  selector: 'app-edit-personal-description-dialog',
  templateUrl: './edit-personal-description-dialog.component.html',
  styleUrls: ['./edit-personal-description-dialog.component.css']
})
export class EditPersonalDescriptionDialogComponent implements OnInit {

  instructorId: number = 0;
  personalDescription: String = '';

  constructor(public dialogRef: MatDialogRef<EditPersonalDescriptionDialogComponent>, private _myUserService: MyUserService, private _alertService: AlertService) { }

  ngOnInit() {
  }

  editPersonalDescription(){
    if(this.personalDescription == ''){
      this._alertService.warning('Lični opis ne može biti prazan!');
    } else{
      this._myUserService.editInstructorPersonalDescription(this.instructorId, this.personalDescription).subscribe(
        (ok: Boolean) => {
          this.dialogRef.close();
        },
        (error) => {
          // console.log(error)
        }
      )
    }
  }

}
