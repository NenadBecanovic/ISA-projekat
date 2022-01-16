import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Address} from "../../model/address";
import {MyUser} from "../../model/my-user";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/user';

  isLoggedIn(): boolean {
    return this.getToken() !== '';
  }

  getToken(): string {
    // @ts-ignore
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    //  console.log(currentUser);
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
  }

  isLoggedInUser(): boolean {
    return this.getToken() !== '' && this.getCurrentUser().roles.indexOf('ROLE_USER') !== -1;
  }

  isLoggedInAdmin(): boolean {
    return this.getToken() !== '' && this.getCurrentUser().roles.indexOf('ROLE_ADMINISTRATOR') !== -1;
  }

  isLoggedInHouseOwner(): boolean {
    return this.getToken() !== '' && this.getCurrentUser().roles.indexOf('ROLE_HOUSE_OWNER') !== -1;
  }

  isLoggedInBoatOwner(): boolean {
    return this.getToken() !== '' && this.getCurrentUser().roles.indexOf('ROLE_BOAT_OWNER') !== -1;
  }

  isLoggedInInstructor(): boolean {
    return this.getToken() !== '' && this.getCurrentUser().roles.indexOf('ROLE_INSTRUCTOR') !== -1;
  }

  getUserEmail(): string{
    return this.getCurrentUser().email;
  }

  getCurrentUser(): any {
    if (localStorage['currentUser']) {
      return JSON.parse(localStorage['currentUser']);
    } else {
      return undefined;
    }
  }

  public getUserByEmail(): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByEmail/`+this.getCurrentUser().email)
  }

}
