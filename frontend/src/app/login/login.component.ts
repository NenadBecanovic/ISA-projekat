import { Component, OnInit } from '@angular/core';
import {AuthUserDTO} from "../model/AuthUserDTO";
import {AuthService} from "../service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService) { }

  authUser = new AuthUserDTO("","");

  ngOnInit(): void {
  }

  loginUser() {

    this.authService.login(this.authUser).subscribe(
      () => {
      console.log("uspeh")

      },
      error=> {
        console.log('error occuried');

      }
    )
  }
}
