import { Component, OnInit } from '@angular/core';
import {AuthUserDTO} from "../model/AuthUserDTO";
import {AuthService} from "../service/auth.service";
import {MyUser} from "../model/my-user";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  constructor(private authService: AuthService, private _router: Router) { }

  authUser = new AuthUserDTO("","");

  ngOnInit(): void {
  }

  loginUser() {

    this.authService.login(this.authUser).subscribe(

      (user:MyUser) => {
      console.log(user)

      },

      error=> {

        console.log('error occuried');

      }
    )
  }

  goToRegisterPage() {
    this._router.navigate(['register']);
  }

}
