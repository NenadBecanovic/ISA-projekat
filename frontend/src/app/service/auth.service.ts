import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthUserDTO} from "../model/AuthUserDTO";
import {MyUser} from "../model/my-user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/indentity';

  public login(authUser: AuthUserDTO): Observable<MyUser>{
    return this._http.post<MyUser>(`${this.userPath}/login`, authUser)
  }

  public register(myUser: MyUser): Observable<MyUser>{
    return  this._http.post<MyUser>(`${this.userPath}/register`, myUser);
  }

  public confirmEmail(urlParams: any) {
    return this._http.post(this.userPath + 'email/verification', urlParams);
  }
}
