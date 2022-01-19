import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import {AuthentificationService} from "./auth/authentification/authentification.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardGuard implements CanActivate {

  constructor(private _authentificationService:AuthentificationService, private _router: Router) {
  }


  canActivate(next: ActivatedRouteSnapshot,state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let url: string = state.url;
    if(!next.data['role']){
      return true;
    }else if(this._authentificationService.isLoggedInUser() && next.data['role'].indexOf('ROLE_USER') !== -1){
      return true;
    }else if(this._authentificationService.isLoggedInAdmin() && next.data['role'].indexOf('ROLE_ADMINISTRATOR') !== -1){
      return true;
    }else if(this._authentificationService.isLoggedInHouseOwner() && next.data['role'].indexOf('ROLE_HOUSE_OWNER') !== -1){
      return true;
    }else if(this._authentificationService.isLoggedInBoatOwner() && next.data['role'].indexOf('ROLE_BOAT_OWNER') !== -1){
      return true;
    }else if(this._authentificationService.isLoggedInInstructor() && next.data['role'].indexOf('ROLE_INSTRUCTOR') !== -1){
      return true;
    }else {
      this._authentificationService.logout();
      this._router.navigate(['login'])
      return false;
    }
  }

}
