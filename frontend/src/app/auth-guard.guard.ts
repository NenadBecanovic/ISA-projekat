import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import {AuthentificationService} from "./auth/authentification/authentification.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardGuard implements CanActivate {

  constructor(private _authentificationService:AuthentificationService, private _router: Router) {
  }


  canActivate() : boolean {

    if(this._authentificationService.isLoggedInUser())
      return true;
    else {
      this._router.navigate(['login'])
      return false;
    }
  }

}
