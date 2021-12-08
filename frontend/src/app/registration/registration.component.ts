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

  address: Address = new Address("","","",0,0)
  user: MyUser = new MyUser("","","","","","",this.address);
  passwordCheck: String = "";


  constructor(private authService: AuthService, private alertService: AlertService) {


  }


  ngOnInit(): void {
  }


  registerUser() {
    if(this.user.password !== this.passwordCheck){
      this.alertService.success('Incorrect password');
      return;
    }
    this.authService.register(this.user).subscribe(
      (user:MyUser)=>{

        this.alertService.success('User created');
      },
      (error) => {
        this.alertService.danger('Something went wrongs');

      },


    )

  }

}
