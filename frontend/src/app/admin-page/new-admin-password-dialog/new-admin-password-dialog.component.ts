import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { Address } from 'src/app/model/address';
import { MyUser } from 'src/app/model/my-user';
import { MyUserService } from 'src/app/service/my-user.service';
import * as bcrypt from 'bcryptjs';

@Component({
  selector: 'app-new-admin-password-dialog',
  templateUrl: './new-admin-password-dialog.component.html',
  styleUrls: ['./new-admin-password-dialog.component.css']
})
export class NewAdminPasswordDialogComponent implements OnInit {

  address: Address = new Address(0,"","","",0,0, 0)
  admin: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  newPassword: string = '';
  newPasswordCheck: string = '';
  oldPassword: string = '';
  passwordCheck: boolean = false;
  password: String = 'Promeni lozinku'
  constructor(public dialogRef: MatDialogRef<NewAdminPasswordDialogComponent>, private _myUserService: MyUserService,
    private alertService: AlertService) {
      dialogRef.disableClose = true;
     }

  ngOnInit() {
  }

  ok() {
      if(this.newPassword === this.newPasswordCheck){
        const salt = bcrypt.genSaltSync(10);
        var isValid = bcrypt.compareSync(this.oldPassword, this.admin.password)
        if(isValid){
          this.admin.passwordChange = true;
          this.admin.password = this.newPassword
          this._myUserService.updateUser(this.admin).subscribe(   // subscribe - da bismo dobili odgovor beka
            (user: MyUser) => {
              this.alertService.success('Uspešno izmenjena lozinka');
              this.dialogRef.close();
            },
            (error) => {
              this.alertService.danger('Greska prilikom promene');
            },
          )
        }else{
          this.alertService.danger('Unesite ponovo staru lozinku');
        }
      }else{
        this.alertService.danger('Proverite novu lozinku');
      }

  }

  changePassword() {
    if(this.password === 'Promeni lozinku'){
      this.password = 'Odustani';
      this.passwordCheck = true;
    }else{
      this.password = 'Promeni lozinku';
      this.passwordCheck = false;
    }
  }

}
