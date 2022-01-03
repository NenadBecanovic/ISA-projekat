import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {AuthUserDTO} from "../model/AuthUserDTO";
import {MyUser} from "../model/my-user";
import {UserTokenState} from "../model/user-token-state";
import {Address} from "../model/address";
import {JwtUtilsService} from "./jwt-utils-service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private _http:HttpClient, private jwtUtilsService: JwtUtilsService) { }

  private readonly userPath = 'http://localhost:8080/api/identity';
  private access_token: any;

  public login(user:AuthUserDTO):Observable<void>{

    const body = {
      email: user.email,
      password: user.password
    };

    return this._http.post<UserTokenState>(`${this.userPath}/login`,body)
      .pipe(map((res) => {
        const email = body.email;
        this.access_token = res.accessToken;
        const token = this.access_token;
        localStorage.setItem('currentUser', JSON.stringify({
          email,
          roles: this.jwtUtilsService.getRoles(token),
          token
        }));
      }));
  }

  public register(myUser: MyUser): Observable<MyUser>{
    return  this._http.post<MyUser>(`${this.userPath}/register`, myUser);
  }

  public confirmEmail(urlParams: any) {
    return this._http.post(this.userPath + '/email/verification', urlParams);
  }
}
