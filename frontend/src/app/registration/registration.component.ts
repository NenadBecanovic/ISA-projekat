import { Component, OnInit } from '@angular/core';
import {MyUser} from "../model/my-user";
import {AuthService} from "../service/auth.service";
import {Address} from "../model/address";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  address: Address = new Address("","","",0,0)
  user: MyUser = new MyUser("","","","","","",this.address);
  passwordCheck: String = "";

  constructor(private authService: AuthService) {

  }

  ngOnInit(): void {
  }

  registerUser() {
    console.log(this.user);
    this.authService.register(this.user).subscribe(
      (user:MyUser)=>{

      },
      (error) => {

      }
    )

  }
}
