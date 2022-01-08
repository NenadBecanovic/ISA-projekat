import { Component, OnInit } from '@angular/core';
import {MyUser} from "../model/my-user";
import {AuthService} from "../service/auth.service";
import {Address} from "../model/address";

import { AlertService } from 'ngx-alerts';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  address: Address = new Address(0,"","","",0,0, 0)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  passwordCheck: String = "";
  isUser = true;

  constructor(private authService: AuthService, private alertService: AlertService) {
  }

  ngOnInit(): void {  // tu pozivam metode iz servisa
  }

  registerUser() {

      if (this.user.password !== this.passwordCheck) {
        this.alertService.success('Incorrect password');
        return;
      }
      this.authService.register(this.user).subscribe(   // subscribe - da bismo dobili odgovor beka
        (isCraated: Boolean) => {
          this.alertService.success('User created');
        },
        (error) => {
          this.alertService.danger('Email nije validan');
        },
      )
  }

  onChange($event: Event) {
    console.log(this.user.authority)
    if(this.user.authority !== "ROLE_USER"){
      this.isUser = false
    }else{
      this.isUser = true
    }
  }
}
