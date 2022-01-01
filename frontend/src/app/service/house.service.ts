import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {House} from "../model/house";

@Injectable({
  providedIn: 'root'
})
export class HouseService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/house';

  public getHouseById(id: number): Observable<House>{
    //console.log(`${this.userPath}/getHouseById/`+id)
    return this._http.get<House>(`${this.userPath}/getHouseById/`+id)
  }
}
