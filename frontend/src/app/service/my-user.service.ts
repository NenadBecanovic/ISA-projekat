import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Image} from "../model/image";
import {MyUser} from "../model/my-user";

@Injectable({
  providedIn: 'root'
})
export class MyUserService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/user';

  public getAllByHouseId(id: number): Observable<MyUser[]>{
    return this._http.get<MyUser[]>(`${this.userPath}/getAllByHouseId/`+id)
  }
}
