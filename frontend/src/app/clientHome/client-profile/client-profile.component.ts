import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {Address} from "../../model/address";
import {MyUser} from "../../model/my-user";
import * as bcrypt from 'bcryptjs';
import {MyUserService} from "../../service/my-user.service";
import {AlertService} from "ngx-alerts";
import {AuthentificationService} from "../../auth/authentification/authentification.service";

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.css']
})
export class ClientProfileComponent implements OnInit {

  ngOnInit(): void {
    this.loadData()
  }
  address: Address = new Address(0,"","","",0,0, 0)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  newPassword: string = '';
  newPasswordCheck: string = '';
  oldPassword: string = '';
  passwordCheck: boolean = false;
  password: String = 'Promeni lozinku'

  constructor(public dialogRef: MatDialogRef<ClientProfileComponent>, private _myUserService: MyUserService,
              private alertService: AlertService, private _authentification: AuthentificationService) {
  }


  onNoClick(): void {
    this.dialogRef.close();
  }

  ok() {
    if(this.passwordCheck){
      if(this.newPassword === this.newPasswordCheck){
        const salt = bcrypt.genSaltSync(10);
        var isValid = bcrypt.compareSync(this.oldPassword, this.user.password)
        if(isValid){
          this.user.passwordChange = true;
          this.user.password = this.newPassword
          this._myUserService.updateUser(this.user).subscribe(   // subscribe - da bismo dobili odgovor beka
            (user: MyUser) => {
              this.alertService.success('Uspjesno izmijenjen nalog');
            },
            (error) => {
              this.alertService.danger('Greska prilikom promijene');
            },
          )
        }else{
          this.alertService.danger('Unesite ponovo staru lozinku');
        }
      }else{
        this.alertService.danger('Provjerite novu lozinku');
      }
    }else {
      this.user.passwordChange = false;
      this._myUserService.updateUser(this.user).subscribe(   // subscribe - da bismo dobili odgovor beka
        (user: MyUser) => {
          this.alertService.success('Uspjesno izmijenjen nalog');
        },
        (error) => {
          this.alertService.danger('Greska prilikom promijene');
        },
      )
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

  private loadData() {
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        console.log(this.user)
      },
      (error) => {
      },
    )
  }
}
