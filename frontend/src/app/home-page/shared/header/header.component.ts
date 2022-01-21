import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthentificationService } from 'src/app/auth/authentification/authentification.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isUserLoggedIn: boolean = false;
  isAdmin: boolean = false;

  constructor(private _router: Router, private _authentificationService: AuthentificationService) { }

  ngOnInit(): void {
    if(this._authentificationService.isLoggedIn()){
      this.isUserLoggedIn = true;
    }
    if(this._authentificationService.isLoggedInAdmin()){
      this.isAdmin = true;
    }
  }

  logout() {
    this._authentificationService.logout();
    this._router.navigate(['']);
  }

  goToHomepage(){
    if (this._authentificationService.isLoggedInUser()) {
      this._router.navigate(['client']);
      // this.getLoggedUser();
    } else if (this._authentificationService.isLoggedInAdmin()) {
      this._router.navigate(['admin-page']);
    } else if (this._authentificationService.isLoggedInHouseOwner()) {
      this._router.navigate(['home-page-house-owner']);
    } else if (this._authentificationService.isLoggedInBoatOwner()) {
      this._router.navigate(['home-page-boat-owner']);
    } else if (this._authentificationService.isLoggedInInstructor()) {
      this._router.navigate(['fishing-instructor']);
    }else{
      this._router.navigate(['']);
    }
  }

  goToAll(){
    this._router.navigate(['']);
  }
}
