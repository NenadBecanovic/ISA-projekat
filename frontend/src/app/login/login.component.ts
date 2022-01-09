import { Component, OnInit } from '@angular/core';
import {AuthUserDTO} from "../model/AuthUserDTO";
import {AuthService} from "../service/auth.service";
import {MyUser} from "../model/my-user";
import {Router} from "@angular/router";
import {AuthentificationService} from "../authentification/authentification.service";
import {AlertService} from "ngx-alerts";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  constructor(private _service: AuthService,private _authenticationService: AuthentificationService, private _router: Router,
              private alertService: AlertService) { }

  authUser = new AuthUserDTO("","");

  ngOnInit(): void {
  }

  loginUser() {
    this._service.login(this.authUser).subscribe(
      () => {
        if (this._authenticationService.isLoggedInUser()) {
          this._router.navigate(['client']);
          // this.getLoggedUser();
        } else if (this._authenticationService.isLoggedInAdmin()) {

        } else if (this._authenticationService.isLoggedInHouseOwner()) {
          this._router.navigate(['home-page-house-owner']);
        } else if (this._authenticationService.isLoggedInBoatOwner()) {
          this._router.navigate(['home-page-boat-owner']);
        }

      }
      ,
      (error)=> {
        console.log('error occuried');
        this.alertService.danger('Pogresni kredencijali');
      }
    )
  }

  goToRegisterPage() {
    this._router.navigate(['register']);
  }

}
